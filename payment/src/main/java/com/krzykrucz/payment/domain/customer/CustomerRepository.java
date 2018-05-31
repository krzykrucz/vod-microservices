package com.krzykrucz.payment.domain.customer;

import java.util.Optional;

public interface CustomerRepository {

    void save(Customer customer);

    Optional<Customer> findOne(CustomerId customerId);
}
