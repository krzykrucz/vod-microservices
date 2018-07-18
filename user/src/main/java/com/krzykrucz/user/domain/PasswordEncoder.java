package com.krzykrucz.user.domain;

public interface PasswordEncoder {

    Password encode(String passwordText);

    boolean passwordMatchesWithString(Password password, String rawString);
}
