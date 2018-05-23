package com.krzykrucz.user.domain;

public interface EventPublisher {

    void publish(UserDomainEvent event);
}
