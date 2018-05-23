package com.krzykrucz.user.domain;

import lombok.Value;

@Value
public class UserCreatedEvent implements UserDomainEvent {

    private final UserId userId;

    private final UserName userName;
}
