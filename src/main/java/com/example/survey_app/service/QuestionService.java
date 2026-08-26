package com.example.survey_app.service;

import java.util.List;

import com.example.survey_app.dto.QuestionCreateRequest;
import com.example.survey_app.dto.QuestionResponse;

public interface QuestionService {
public QuestionResponse addQuestionToSurvey(Long surveyId, QuestionCreateRequest questionRequest);
public List<QuestionResponse> getQuestionbySurvey(Long surveyId); 
}
