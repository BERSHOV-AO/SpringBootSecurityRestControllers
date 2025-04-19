package ru.kata.springbootsecurityrestcontrollers.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.springbootsecurityrestcontrollers.models.User;
import ru.kata.springbootsecurityrestcontrollers.services.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile_user")
    public User user() {
        return userService.oneUser();
    }
}
