package com.krzykrucz.payment.application;

import com.krzykrucz.payment.domain.CurrentCustomerProvider;
import com.krzykrucz.payment.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerQueryController {

    private final CurrentCustomerProvider currentCustomerProvider;

    @Autowired
    public CustomerQueryController(CurrentCustomerProvider currentCustomerProvider) {
        this.currentCustomerProvider = currentCustomerProvider;
    }

    @GetMapping("/current")
    Customer getCurrentCustomer() {
        return currentCustomerProvider.getCurrentCustomer();
    }

}
