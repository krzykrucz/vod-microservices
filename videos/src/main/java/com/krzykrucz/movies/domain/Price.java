package com.krzykrucz.movies.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.krzykrucz.movies.application.MoneySerializer;
import lombok.Value;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import static com.google.common.base.Preconditions.checkArgument;

@Value
public class Price {

    @JsonSerialize(using = MoneySerializer.class)
    private final Money money;

    public static Price fromUSD(int value) {
        checkArgument(value > 0);
        return new Price(Money.of(CurrencyUnit.USD, value));
    }

    public boolean isZero() {
        return money.isZero();
    }
}
