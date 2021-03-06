package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerName;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

interface MongoCustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findByCustomerName(CustomerName customerName);

}
