package com.krzykrucz.frontend.ui;

import com.krzykrucz.frontend.infrastructure.ApiEndpoints;
import com.krzykrucz.frontend.infrastructure.ApiGatewayClient;
import com.krzykrucz.frontend.infrastructure.PaymentViewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PaymentController {

    private final ApiGatewayClient client;

    public PaymentController(ApiGatewayClient client) {
        this.client = client;
    }

    @GetMapping("/buy/{viewerName}")
    String buy(@ModelAttribute("videoTitle") String videoTitle, Model model, HttpServletRequest request,
               @PathVariable("viewerName") String viewerName, HttpServletResponse httpServletResponse) {

        final String hostname = request.getRequestURL().toString()
                .replaceAll("buy.*", "");
        final String successUrl = hostname + "executePayment/" + viewerName;
        final String cancelUrl = hostname + "cancelPayment/" + viewerName;

        final ResponseEntity<PaymentViewDto> response = client.post(ApiEndpoints.BUY_VIDEO, new BuyRequest(videoTitle, cancelUrl, successUrl, viewerName));

        model.addAttribute("paypalView", response.getBody().getViewUrl());
        return "paypal";
//        httpServletResponse.setHeader("Location", response.getBody().getViewUrl());
    }

    @PostMapping("/executePayment/{currentCustomerName}")
    String onSuccess(HttpServletRequest request, @PathVariable("currentCustomerName") String currentCustomerName) {
        client.post(ApiEndpoints.EXECUTE_PAYMENT, new ExecutePaymentRequest("", "", currentCustomerName));
        return "watch";
    }

    @PostMapping("/cancelPayment/{currentCustomerName}")
    String onCancel(HttpServletRequest request, @PathVariable("currentCustomerName") String currentCustomerName) {
//        client.post(ApiEndpoints.EXECUTE_PAYMENT, new ExecutePaymentRequest("", "", currentCustomerName));
        return "Payment cancelled";
    }

}
