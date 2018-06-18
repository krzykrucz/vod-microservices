package com.krzykrucz.movies.infrastructure.viewer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment")
interface CustomerClient {

    @GetMapping("/customer")
    CustomerDTO getCurrentCustomer(@RequestParam("username") String username);

}
