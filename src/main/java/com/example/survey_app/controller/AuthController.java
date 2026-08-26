package com.example.survey_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.survey_app.dto.LoginRequest;
import com.example.survey_app.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
    String result = authService.login(loginRequest, request, response);
    return ResponseEntity.ok(result);
}
}
