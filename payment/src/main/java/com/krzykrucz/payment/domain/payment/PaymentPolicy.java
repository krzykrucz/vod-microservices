package com.krzykrucz.payment.domain.payment;

import com.krzykrucz.payment.domain.movie.Movie;
import com.krzykrucz.payment.domain.movie.MovieRequestPayload;
import io.vavr.control.Try;

public interface PaymentPolicy {

    Try<Void> executePayment(Payment payment, PayerId payerId);

    Try<Payment> createPaymentForMovie(Movie movie, MovieRequestPayload movieRequestPayload);
}
