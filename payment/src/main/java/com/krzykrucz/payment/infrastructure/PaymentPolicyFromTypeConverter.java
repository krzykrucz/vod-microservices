package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.payment.PaymentPolicy;
import com.krzykrucz.payment.domain.payment.paypal.PayPalPaymentPolicy;
import org.springframework.core.convert.converter.Converter;

public class PaymentPolicyFromTypeConverter implements Converter<String, PaymentPolicy> {

    private final PayPalPaymentPolicy payPalPaymentPolicy;

    public PaymentPolicyFromTypeConverter(PayPalPaymentPolicy payPalPaymentPolicy) {
        this.payPalPaymentPolicy = payPalPaymentPolicy;
    }

    @Override
    public PaymentPolicy convert(String paymentPolicyType) {
        if (PaymentPolicyType.valueOf(paymentPolicyType).equals(PaymentPolicyType.PAYPAL)) {
            return payPalPaymentPolicy;
        }
        return null;
    }
}
