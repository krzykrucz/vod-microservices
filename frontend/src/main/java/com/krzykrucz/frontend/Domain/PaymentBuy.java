package com.krzykrucz.frontend.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentBuy{
    public String successfulViewUrl;
    public String cancelViewUrl;
    public String title;
    public String currentCustomerName;
}