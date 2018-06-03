package com.krzykrucz.payment.domain.customer;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.krzykrucz.payment.domain.movie.Movie;
import com.krzykrucz.payment.domain.movie.MovieRequestPayload;
import com.krzykrucz.payment.domain.payment.*;
import io.vavr.control.Try;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@EqualsAndHashCode(of = "customerId")
public class Customer {

    @Getter
    private final CustomerId customerId;
    @Getter
    private final Set<Movie> purchasedMovies = Sets.newHashSet();
    private final PaymentPolicy paymentPolicy;
    private final Map<PaymentId, Payment> currentPayments = Maps.newHashMap();
    @Getter
    private CustomerName customerName;

    private Customer(CustomerId customerId, CustomerName customerName, PaymentPolicy paymentPolicy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.paymentPolicy = paymentPolicy;
    }

    public static Customer createNew(CustomerName name, PaymentPolicy paymentPolicy) {
        return new Customer(CustomerId.newId(), name, paymentPolicy);
    }

    public Try<PaymentView> requestMovie(Movie movie, MovieRequestPayload requestPayload) {
        purgePayments();

        return paymentPolicy.createPaymentForMovie(movie, requestPayload)
                .onSuccess(payment -> currentPayments.put(payment.getPaymentId(), payment))
                .map(Payment::getPaymentView);
    }

    public void cancelMoviePayment(PaymentId paymentId) {
        purgePayments();

        checkState(currentPayments.containsKey(paymentId));
        currentPayments.remove(paymentId);
    }

    public Try<Void> confirmMoviePayment(PaymentId paymentId, PayerId payerId) {
        purgePayments();

        checkState(currentPayments.containsKey(paymentId));

        currentPayments.get(paymentId).confirm();

        final Payment payment = currentPayments.get(paymentId);
        final Try<Void> executionResult = paymentPolicy.executePayment(payment, payerId);

        if (payment.getStatus() == Payment.Status.EXECUTED) {
            currentPayments.remove(paymentId);

            final Movie purchasedMovie = payment.getPaidMovie();
            purchasedMovies.add(purchasedMovie);

        } else {
            currentPayments.remove(paymentId);
        }
        return executionResult;
    }

    private void purgePayments() {
        currentPayments.entrySet()
                .removeIf(entry -> entry.getValue().isPurgable());
    }

}
