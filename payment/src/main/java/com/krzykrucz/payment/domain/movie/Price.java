package com.krzykrucz.payment.domain.movie;

import lombok.Value;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Value
public class Price {
    private final Money money;

    public static Price zero(CurrencyUnit currencyUnit) {
        return new Price(Money.zero(currencyUnit));
    }
}
