package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.customer.CurrentCustomerProvider;
import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerName;
import com.krzykrucz.payment.domain.payment.paypal.PayPalPaymentPolicy;
import com.krzykrucz.payment.domain.payment.paypal.PayPalService;
import org.springframework.stereotype.Component;

@Component
class CurrentCustomerProviderImpl implements CurrentCustomerProvider {

    private final PayPalService payPalService;

    CurrentCustomerProviderImpl(PayPalService payPalService) {
        this.payPalService = payPalService;
    }

    @Override
    public Customer getCurrentCustomer() {
        // TODO access users service
        return mockCustomer();
    }

    private Customer mockCustomer() {
        return Customer.createNew(new CustomerName("customer"), new PayPalPaymentPolicy(payPalService));
    }
}
