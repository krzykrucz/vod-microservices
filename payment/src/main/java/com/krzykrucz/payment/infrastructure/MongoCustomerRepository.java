package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface MongoCustomerRepository extends MongoRepository<Customer, CustomerId> {
}
