package com.krzykrucz.user.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserId implements Serializable {

    private final UUID uuid;
}
