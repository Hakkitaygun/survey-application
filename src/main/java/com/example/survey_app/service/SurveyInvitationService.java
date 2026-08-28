package com.example.survey_app.service;

import com.example.survey_app.dto.request.SurveyInvitationRequest;

public interface SurveyInvitationService {
void sendEmail(Long surveyId,SurveyInvitationRequest surveyInvitationRequest);
}
