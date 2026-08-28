package com.example.survey_app.service;

import java.util.List;

import com.example.survey_app.dto.request.SurveyRequest;
import com.example.survey_app.dto.response.SurveyResponse;
import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.SurveyInvitation;

public interface SurveyService {
public List<SurveyResponse> getAllSurveys();
public Survey createSurvey(Survey survey);
public List<SurveyInvitation> getSurveyResult(Long surveyId);
public SurveyResponse updateSurvey(Long surveyId, SurveyRequest surveyRequest);
public Survey deleteSurvey(Long surveyId); 
}
