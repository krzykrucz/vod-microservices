package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.customer.CurrentCustomerProvider;
import com.krzykrucz.payment.domain.customer.Customer;
import org.springframework.stereotype.Component;

@Component
class CurrentCustomerProviderImpl implements CurrentCustomerProvider {
    @Override
    public Customer getCurrentCustomer() {
        // TODO access users service
        return null;
    }
}
