package com.krzykrucz.payment.domain.customer;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.krzykrucz.payment.domain.Movie;
import com.krzykrucz.payment.domain.payment.Payment;
import com.krzykrucz.payment.domain.payment.PaymentId;
import com.krzykrucz.payment.domain.payment.PaymentPolicy;
import com.krzykrucz.payment.domain.payment.PaymentView;
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

    public PaymentView requestMovie(Movie movie) {
        purgePayments();

        final Payment payment = paymentPolicy.createPaymentForMovie(movie);
        currentPayments.put(payment.getPaymentId(), payment);

        return payment.getPaymentView();
    }

    public void cancelMoviePayment(PaymentId paymentId) {
        purgePayments();

        checkState(currentPayments.containsKey(paymentId));
        currentPayments.remove(paymentId);
    }

    public void confirmMoviePayment(PaymentId paymentId) {
        purgePayments();

        checkState(currentPayments.containsKey(paymentId));

        currentPayments.get(paymentId).confirm();

        final Payment payment = currentPayments.get(paymentId);
        paymentPolicy.executePayment(payment);

        if (payment.getStatus() == Payment.Status.EXECUTED) {
            currentPayments.remove(paymentId);

            final Movie purchasedMovie = payment.getPaidMovie();
            purchasedMovies.add(purchasedMovie);

        } else {
            currentPayments.remove(paymentId);
        }
    }

    private void purgePayments() {
        currentPayments.entrySet()
                .removeIf(entry -> entry.getValue().isPurgable());
    }

}
