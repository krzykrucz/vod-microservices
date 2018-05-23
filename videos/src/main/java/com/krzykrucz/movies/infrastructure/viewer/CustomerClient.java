package com.krzykrucz.movies.infrastructure.viewer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "payment")
interface CustomerClient {

    @GetMapping("/customer/current")
    CustomerDTO getCurrentCustomer();

}
