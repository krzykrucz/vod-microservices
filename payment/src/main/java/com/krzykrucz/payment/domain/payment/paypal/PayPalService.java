package com.krzykrucz.payment.domain.payment.paypal;

import com.krzykrucz.payment.domain.payment.PayerId;
import com.krzykrucz.payment.domain.payment.PaymentId;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.joda.money.Money;

public interface PayPalService {
    Payment createPayment(
            Money paymentValue,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException;

    Payment executePayment(PaymentId paymentId, PayerId payerId) throws PayPalRESTException;
}
