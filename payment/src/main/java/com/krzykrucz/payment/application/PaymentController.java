package com.krzykrucz.payment.application;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/payment")
public class PaymentController {

    @PostMapping
    public void buyMovie(@RequestBody BuyMovieCommand command) {

    }

}
