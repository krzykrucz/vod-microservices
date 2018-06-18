package com.krzykrucz.payment.application.customer;

import com.krzykrucz.payment.domain.customer.Customer;
import com.krzykrucz.payment.domain.customer.CustomerName;
import com.krzykrucz.payment.domain.customer.CustomerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerQueryController {

    private final CustomerProvider customerProvider;

    @Autowired
    public CustomerQueryController(CustomerProvider customerProvider) {
        this.customerProvider = customerProvider;
    }

    @GetMapping("/current")
    Customer getCurrentCustomer(@RequestParam("username") String username) {
        return customerProvider.getCustomerByName(new CustomerName(username));
    }

}
