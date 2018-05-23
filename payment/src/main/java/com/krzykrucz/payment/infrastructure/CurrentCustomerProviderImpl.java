package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.CurrentCustomerProvider;
import com.krzykrucz.payment.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CurrentCustomerProviderImpl implements CurrentCustomerProvider {
    @Override
    public Customer getCurrentCustomer() {
        // TODO access users service
        return null;
    }
}
