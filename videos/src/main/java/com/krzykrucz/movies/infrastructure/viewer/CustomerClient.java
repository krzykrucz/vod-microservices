package com.krzykrucz.movies.infrastructure.viewer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment")
interface CustomerClient {

    @GetMapping("/customer/current/{username}")
    CustomerDTO getCurrentCustomer(@PathVariable("username") String username);

}
