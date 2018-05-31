package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerId;
import com.krzykrucz.payment.domain.customer.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class CustomerRepositoryImpl implements CustomerRepository {

    private final MongoCustomerRepository mongoCustomerRepository;

    public CustomerRepositoryImpl(MongoCustomerRepository mongoCustomerRepository) {
        this.mongoCustomerRepository = mongoCustomerRepository;
    }

    @Override
    public void save(Customer customer) {
        mongoCustomerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findOne(CustomerId customerId) {
        return mongoCustomerRepository.findById(customerId);
    }

}
