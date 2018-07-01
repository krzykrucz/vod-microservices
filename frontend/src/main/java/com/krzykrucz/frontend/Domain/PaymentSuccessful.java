package com.krzykrucz.frontend.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentSuccessful {
    public String paymentId;
    public String payerId;
    public String currentCustomerName;
}