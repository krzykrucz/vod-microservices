package com.krzykrucz.payment.domain.payment.paypal;

import com.krzykrucz.payment.domain.Movie;
import com.krzykrucz.payment.domain.payment.Payment;
import com.krzykrucz.payment.domain.payment.PaymentPolicy;

public class PayPalPaymentPolicy implements PaymentPolicy {

    private final PayPalService payPalService;

    public PayPalPaymentPolicy(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @Override
    public void executePayment(Payment payment) {

    }

    @Override
    public Payment createPaymentForMovie(Movie movie) {
        return null;
    }
}
