package com.krzykrucz.user.init;

import com.krzykrucz.user.domain.UserFactory;
import com.krzykrucz.user.domain.UserName;
import com.krzykrucz.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class EnvInitializer implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserFactory userFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!userRepository.findByName_Name("Barbossa").isPresent()) {
            userFactory.createNewUser(new UserName("Barbossa"), "unsafe");
        }
    }
}
