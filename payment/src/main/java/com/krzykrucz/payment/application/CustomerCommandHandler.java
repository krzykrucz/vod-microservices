package com.krzykrucz.payment.application;

import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerFactory;
import com.krzykrucz.payment.domain.customer.CustomerName;
import com.krzykrucz.payment.domain.customer.CustomerRepository;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandHandler {

    private final CustomerRepository customerRepository;

    private final CustomerFactory customerFactory;

    public CustomerCommandHandler(CustomerRepository customerRepository, CustomerFactory customerFactory) {
        this.customerRepository = customerRepository;
        this.customerFactory = customerFactory;
    }

    @StreamListener(Sink.INPUT)
    void receive(Message<CreateCustomerCommand> message) {
        final CreateCustomerCommand createCustomerCommand = message.getPayload();
        final CustomerName newCustomerName = createCustomerCommand.getCustomerName();
        final Customer newCustomer = customerFactory.newCustomerWithPaypalPaymentPolicy(newCustomerName);
        customerRepository.save(newCustomer);
    }

}
