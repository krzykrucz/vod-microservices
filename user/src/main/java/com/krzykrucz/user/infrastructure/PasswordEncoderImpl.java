package com.krzykrucz.user.infrastructure;

import com.krzykrucz.user.domain.Password;
import com.krzykrucz.user.domain.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderImpl implements PasswordEncoder {

    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordEncoderImpl(org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Password encode(String passwordText) {
        return new Password(passwordEncoder.encode(passwordText));
    }
}
