package com.example.survey_app.service.impl;

import com.example.survey_app.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.survey_app.entity.User;
import com.example.survey_app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
private final PasswordEncoder passwordEncoder;    
private final UserRepository userRepository;
    @Override
    public User createUser(User user) {
        String hashedPasword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPasword);
        return userRepository.save(user);
    }

}
