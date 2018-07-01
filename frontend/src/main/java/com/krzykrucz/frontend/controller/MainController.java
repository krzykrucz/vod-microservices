package com.krzykrucz.frontend.controller;

import com.krzykrucz.frontend.Domain.PaymentBuy;
import com.krzykrucz.frontend.Domain.PaymentSuccessful;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    RestTemplate restTemplate = new RestTemplate();

    private String clientName = "Barbossa";
    private String paymentUri = "http://localhost:18888";

    @GetMapping("/")
    public String homePage() {

        return "homePage";
    }

    @GetMapping(value = "/buy/{title}")
    public ModelAndView buy(@PathVariable String title){
        String address = restTemplate.postForObject(paymentUri + "/payments/buy",
                new PaymentBuy(
                        "localhost:9091/successfulPayment/" + title,
                        "localhost:9091/",
                        "starWars",
                        clientName
                ),
                null,
                String.class
        );

        return new ModelAndView("redirect:" + address);
    }

    @PostMapping(value = "/successfulPayment/{title}")

    public String successfulPayment(@PathVariable String title, Model model) {
        restTemplate.postForObject(
                paymentUri + "payment/success",
                new PaymentSuccessful(
                        "",
                        "",
                        clientName
                ),
                String.class
        );

        model.addAttribute("title", title);

        return "movie";
    }


}
