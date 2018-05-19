package com.krzykrucz.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.money.Money;

@AllArgsConstructor
@Getter
public class Movie {

    private final Money price;

    private final String title;
}
