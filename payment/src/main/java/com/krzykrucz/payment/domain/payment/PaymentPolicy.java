package com.krzykrucz.payment.domain.payment;

import com.krzykrucz.payment.domain.Movie;

public interface PaymentPolicy {

    void executePayment(Payment payment);

    Payment createPaymentForMovie(Movie movie);
}
