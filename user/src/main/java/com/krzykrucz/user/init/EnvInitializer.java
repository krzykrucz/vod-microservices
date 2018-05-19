package com.krzykrucz.user.init;

import com.krzykrucz.user.domain.*;
import com.krzykrucz.user.infrastructure.UserRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EnvInitializer implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final User user1 = new User(
                new UserId(UUID.randomUUID()),
                new UserName("Barbossa"),
                new Password("unsafe"));
        userRepository.save(user1);
    }
}
