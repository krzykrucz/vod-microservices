package com.krzykrucz.frontend.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExecutePaymentRequest {
    private String paymentId;
    private String payerId;
    private String currentCustomerName;

}
