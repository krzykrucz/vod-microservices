package com.krzykrucz.user.domain;

public interface PasswordEncoder {

    Password encode(String passwordText);

}
