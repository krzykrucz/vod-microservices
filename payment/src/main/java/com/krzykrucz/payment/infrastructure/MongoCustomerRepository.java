package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.Customer;
import com.krzykrucz.payment.domain.CustomerId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCustomerRepository extends MongoRepository<Customer, CustomerId> {
}
