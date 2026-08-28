package com.example.survey_app.service;

import com.example.survey_app.dto.request.AnswerSubmitRequest;
import com.example.survey_app.dto.response.SurveyResponse;

public interface AnswerService {
 public SurveyResponse getSurveyByToken(String token);
 public void submitAnswers(AnswerSubmitRequest request);
}
