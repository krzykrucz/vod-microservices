package com.krzykrucz.payment.domain.payment.paypal;

import com.krzykrucz.payment.domain.movie.Movie;
import com.krzykrucz.payment.domain.movie.MovieRequestPayload;
import com.krzykrucz.payment.domain.movie.MovieRequestPayloadPropertyMissing;
import com.krzykrucz.payment.domain.payment.*;
import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;
import io.vavr.control.Try;
import org.joda.money.Money;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.krzykrucz.payment.domain.payment.paypal.PaypalMovieRequestProperty.PAYMENT_CANCEL_VIEW_URL;
import static com.krzykrucz.payment.domain.payment.paypal.PaypalMovieRequestProperty.PAYMENT_SUCCESS_VIEW_URL;

@Component
public class PayPalPaymentPolicy implements PaymentPolicy {

    private final PayPalService payPalService;

    public PayPalPaymentPolicy(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @Override
    public Try<Void> executePayment(Payment payment, PayerId payerId) {
        return Try.run(() -> payPalService.executePayment(payment.getPaymentId(), payerId))
                .onSuccess(e -> payment.executeForPayer(payerId))
                .onFailure(ex -> payment.reject());
    }

    @Override
    public Try<Payment> createPaymentForMovie(Movie movie, MovieRequestPayload movieRequestPayload) {
        final String description = "Payment for " + movie.getTitle();
        final Money paymentValue = movie.getPrice().getMoney();
        final Optional<String> cancelViewUrl = movieRequestPayload.getProperty(PAYMENT_CANCEL_VIEW_URL);
        final Optional<String> successViewUrl = movieRequestPayload.getProperty(PAYMENT_SUCCESS_VIEW_URL);
        if (!successViewUrl.isPresent()) {
            return Try.failure(new MovieRequestPayloadPropertyMissing(PAYMENT_SUCCESS_VIEW_URL));
        } else if (!cancelViewUrl.isPresent()) {
            return Try.failure(new MovieRequestPayloadPropertyMissing(PAYMENT_CANCEL_VIEW_URL));
        }
        return Try.of(() -> payPalService.createPayment(paymentValue, description, cancelViewUrl.get(), successViewUrl.get()))
                .flatMap(payment -> toDomainPayment(payment, movie));
    }

    private Try<Payment> toDomainPayment(com.paypal.api.payments.Payment payment, Movie movie) {
        final PaymentId paymentId = new PaymentId(payment.getId());
        return payment.getLinks().stream()
                .filter(links -> links.getRel().equals("approval_url"))
                .map(Links::getHref)
                .findFirst()
                .map(PaymentView::new)
                .map(paymentUrl -> new Payment(paymentId, paymentUrl, movie))
                .map(Try::success)
                .orElse(Try.failure(new PayPalRESTException("Paypal payment view URL unavailable")));
    }
}
