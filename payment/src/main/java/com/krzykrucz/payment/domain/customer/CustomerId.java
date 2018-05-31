package com.krzykrucz.payment.domain.customer;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class CustomerId implements Serializable {
    private final UUID uuid;

    public static CustomerId newId() {
        return new CustomerId(UUID.randomUUID());
    }
}
