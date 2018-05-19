package com.krzykrucz.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@Getter
public class User {

    @Id
    private final UserId userId;

    @Indexed(unique = true)
    private UserName name;

    private Password password;

}
