package com.krzykrucz.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode(of = "userId")
public class User {

    @Id
    private final UserId userId;

    @Indexed(unique = true)
    private UserName name;

    private Password password;

}
