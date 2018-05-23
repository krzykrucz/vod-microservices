package com.krzykrucz.payment.infrastructure;

import com.krzykrucz.payment.domain.Customer;
import com.krzykrucz.payment.domain.CustomerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final MongoCustomerRepository mongoCustomerRepository;

    public CustomerRepositoryImpl(MongoCustomerRepository mongoCustomerRepository) {
        this.mongoCustomerRepository = mongoCustomerRepository;
    }

    @Override
    public void save(Customer customer) {
        mongoCustomerRepository.save(customer);
    }

}
