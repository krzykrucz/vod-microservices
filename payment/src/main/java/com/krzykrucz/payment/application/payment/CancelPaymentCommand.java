package com.krzykrucz.payment.application.payment;

import com.krzykrucz.payment.domain.customer.CustomerName;
import com.krzykrucz.payment.domain.payment.PaymentId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
class CancelPaymentCommand {

    private String paymentId;

    private String currentCustomerName;

    CustomerName getCurrentCustomerName() {
        return new CustomerName(currentCustomerName);
    }

    PaymentId getPaymentId() {
        return new PaymentId(paymentId);
    }
}
