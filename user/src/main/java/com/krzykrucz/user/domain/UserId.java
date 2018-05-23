package com.krzykrucz.user.domain;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value
public class UserId implements Serializable {

    private final UUID uuid;
}
