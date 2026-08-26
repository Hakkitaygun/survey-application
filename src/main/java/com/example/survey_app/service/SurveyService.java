package com.example.survey_app.service;

import java.util.List;

import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.SurveyInvitation;

public interface SurveyService {
public List<Survey> getAllSurveys();
public Survey createSurvey(Survey survey);
public List<SurveyInvitation> getSurveyResult(Long surveyId);
}
