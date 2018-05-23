package com.krzykrucz.payment.application;

import com.krzykrucz.payment.domain.Customer;
import com.krzykrucz.payment.domain.CustomerId;
import com.krzykrucz.payment.domain.CustomerRepository;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandHandler {

    private final CustomerRepository customerRepository;

    public CustomerCommandHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @StreamListener(Sink.INPUT)
    void receive(Message<CreateCustomerCommand> message) {
        final CreateCustomerCommand createCustomerCommand = message.getPayload();
        final Customer newCustomer = new Customer(CustomerId.newId(), createCustomerCommand.getCustomerName());
        customerRepository.save(newCustomer);
    }

}
