package com.krzykrucz.user.infrastructure;

import com.krzykrucz.user.domain.EventPublisher;
import com.krzykrucz.user.domain.UserDomainEvent;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherImpl implements EventPublisher {

    private final Source messagePublisher;

    public EventPublisherImpl(Source messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void publish(UserDomainEvent event) {
        messagePublisher.output()
                .send(MessageBuilder.withPayload(event).build());
    }
}
