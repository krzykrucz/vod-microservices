package com.krzykrucz.payment.application.payment;

import com.krzykrucz.payment.domain.customer.CustomerName;
import com.krzykrucz.payment.domain.payment.PayerId;
import com.krzykrucz.payment.domain.payment.PaymentId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
class ExecutePaymentCommand {

    private String paymentId;

    private String payerId;

    private String currentCustomerName;

    PayerId getPayerId() {
        return new PayerId(payerId);
    }

    PaymentId getPaymentId() {
        return new PaymentId(paymentId);
    }

    CustomerName getCustomerName() {
        return new CustomerName(currentCustomerName);
    }

}
