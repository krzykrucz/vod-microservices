package com.krzykrucz.user.domain;

import com.krzykrucz.user.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final EventPublisher eventPublisher;

    public UserFactory(PasswordEncoder passwordEncoder, UserRepository userRepository, EventPublisher eventPublisher) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public UserId createNewUser(UserName userName, String password) {
        final UserId userId = new UserId(UUID.randomUUID());
        final Password encodedPassword = passwordEncoder.encode(password);
        final User newUser = new User(userId, userName, encodedPassword);

        userRepository.save(newUser);
        eventPublisher.publish(new UserCreatedEvent(userId, userName));

        return userId;
    }
}
