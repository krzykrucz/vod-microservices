package com.krzykrucz.payment.domain;

import lombok.Value;

@Value
public class Movie {

    private final Price price;

    private final String title;
}
