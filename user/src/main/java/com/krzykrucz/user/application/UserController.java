package com.krzykrucz.user.application;

import com.krzykrucz.user.domain.PasswordEncoder;
import com.krzykrucz.user.domain.User;
import com.krzykrucz.user.infrastructure.UserMongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMongoRepository userMongoRepository;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserMongoRepository userMongoRepository, PasswordEncoder passwordEncoder) {
        this.userMongoRepository = userMongoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Void> login(@RequestBody UserCredentials credentials) {
        final Optional<User> existingUser = userMongoRepository.findByName_Name(credentials.getUserIdentifier());
        if (!existingUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoder.passwordMatchesWithString(existingUser.get().getPassword(), credentials.getPassword())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
