package com.krzykrucz.user.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/current/username")
    String getCurrentUserUsername() {
        return "John Smith";
    }

}
