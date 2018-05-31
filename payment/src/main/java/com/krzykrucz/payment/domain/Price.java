package com.krzykrucz.payment.domain;

import lombok.Value;
import org.joda.money.Money;

@Value
class Price {
    private final Money money;
}
