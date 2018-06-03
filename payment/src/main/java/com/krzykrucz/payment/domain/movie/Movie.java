package com.krzykrucz.payment.domain.movie;

import lombok.Value;

@Value
public class Movie {

    private final Price price;

    private final String title;
}
