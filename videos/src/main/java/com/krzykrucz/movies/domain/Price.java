package com.krzykrucz.movies.domain;

import lombok.Data;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import static com.google.common.base.Preconditions.checkArgument;

@Data
public class Price {

    private final Money money;

    public static Price fromUSD(int value) {
        checkArgument(value > 0);
        return new Price(Money.of(CurrencyUnit.USD, value));
    }
}