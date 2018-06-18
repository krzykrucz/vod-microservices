package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.customer.CustomerId;
import com.krzykrucz.payment.domain.payment.PaymentId;
import com.krzykrucz.payment.domain.payment.PaymentPolicy;
import com.krzykrucz.payment.domain.payment.paypal.PayPalPaymentPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;
import java.util.UUID;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions(PayPalPaymentPolicy policy) {
        return new MongoCustomConversions(Arrays.asList(
                new PaymentPolicyToStringConverter(),
                new PaymentPolicyFromTypeConverter(policy),
                new CustomerIdToUUIDConverter(),
                new UUIDToCustomerIdConverter(),
                new PaymentIdToStringConverter(),
                new StringToPaymentIdConverter()
        ));
    }

    private class PaymentPolicyToStringConverter implements Converter<PaymentPolicy, String> {
        @Override
        public String convert(PaymentPolicy paymentPolicy) {
            if (paymentPolicy instanceof PayPalPaymentPolicy) {
                return PaymentPolicyType.PAYPAL.name();
            }
            return null;
        }
    }

    private class CustomerIdToUUIDConverter implements Converter<CustomerId, UUID> {
        @Override
        public UUID convert(CustomerId customerId) {
            return customerId.getUuid();
        }
    }

    private class UUIDToCustomerIdConverter implements Converter<UUID, CustomerId> {
        @Override
        public CustomerId convert(UUID uuid) {
            return new CustomerId(uuid);
        }
    }

    private class PaymentIdToStringConverter implements Converter<PaymentId, String> {
        @Override
        public String convert(PaymentId paymentId) {
            return paymentId.getId();
        }
    }

    private class StringToPaymentIdConverter implements Converter<String, PaymentId> {
        @Override
        public PaymentId convert(String s) {
            return new PaymentId(s);
        }
    }


}
