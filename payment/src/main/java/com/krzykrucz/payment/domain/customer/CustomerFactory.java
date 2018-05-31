package com.krzykrucz.payment.domain.customer;

import com.krzykrucz.payment.domain.payment.paypal.PayPalPaymentPolicy;
import com.krzykrucz.payment.domain.payment.paypal.PayPalService;
import org.springframework.stereotype.Component;

@Component
public class CustomerFactory {

    private final PayPalService payPalService;

    public CustomerFactory(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    public Customer newCustomerWithPaypalPaymentPolicy(CustomerName name) {
        return Customer.createNew(name, new PayPalPaymentPolicy(payPalService));
    }

}
