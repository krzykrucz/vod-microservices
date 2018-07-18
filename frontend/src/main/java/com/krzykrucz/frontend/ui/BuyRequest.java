package com.krzykrucz.frontend.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BuyRequest {
    private String movieTitle;
    private String cancelViewUrl;
    private String successViewUrl;
    private String currentCustomerName;
}
