package com.krzykrucz.payment.domain.movie;

import lombok.Value;
import org.joda.money.Money;

@Value
public class Price {
    private final Money money;
}
