package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerName;
import com.krzykrucz.payment.domain.customer.CustomerProvider;
import com.krzykrucz.payment.domain.customer.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
class CustomerProviderImpl implements CustomerProvider {

    private final CustomerRepository customerRepository;

    CustomerProviderImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerByName(CustomerName name) {
        return customerRepository.findOne(name)
                .orElseThrow(() -> new RuntimeException("Cannot find customer of name " + name.getName()));
    }

}
