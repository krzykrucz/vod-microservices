package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerName;
import com.krzykrucz.payment.domain.customer.CustomerProvider;
import com.krzykrucz.payment.domain.customer.CustomerRepository;
import com.krzykrucz.payment.domain.payment.paypal.PayPalPaymentPolicy;
import com.krzykrucz.payment.domain.payment.paypal.PayPalService;
import org.springframework.stereotype.Component;

@Component
class CustomerProviderImpl implements CustomerProvider {

    private final PayPalService payPalService;

    private final CustomerRepository customerRepository;

    CustomerProviderImpl(PayPalService payPalService, CustomerRepository customerRepository) {
        this.payPalService = payPalService;
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerByName(CustomerName name) {
        return customerRepository.findOne(name)
                .orElseThrow(() -> new RuntimeException("Cannot find customer of name " + name.getName()));
    }

    private Customer mockCustomer() {
        return Customer.createNew(new CustomerName("customer"), new PayPalPaymentPolicy(payPalService));
    }
}
