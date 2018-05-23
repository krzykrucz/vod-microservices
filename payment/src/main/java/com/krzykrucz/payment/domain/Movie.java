package com.krzykrucz.payment.domain;

import lombok.Value;
import org.joda.money.Money;

@Value
public class Movie {

    private final Money price;

    private final String title;
}
