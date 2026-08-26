package com.example.survey_app.service;

import com.example.survey_app.dto.AnswerSubmitRequest;
import com.example.survey_app.dto.SurveyResponse;

public interface AnswerService {
 public SurveyResponse getSurveyByToken(String token);
 public void submitAnswers(AnswerSubmitRequest request);
}
