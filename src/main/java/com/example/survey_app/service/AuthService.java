package com.example.survey_app.service;

import com.example.survey_app.dto.request.LoginRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
String login(LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
