package com.example.survey_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.survey_app.entity.User;
import com.example.survey_app.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/User")
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        
        
        return userService.createUser(user);
    }


}
