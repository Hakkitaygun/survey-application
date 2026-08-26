package com.example.survey_app.service;

import com.example.survey_app.dto.SurveyInvitationRequest;

public interface SurveyInvitationService {
void sendEmail(Long surveyId,SurveyInvitationRequest surveyInvitationRequest);
}
