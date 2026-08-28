package com.example.survey_app.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.survey_app.dto.request.SurveyInvitationRequest;
import com.example.survey_app.service.SurveyInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
// URL'yi REST standartlarına göre güncelledik (başındaki / işaretini unutmamak önemli)
@RequestMapping("/api/v1/surveys") 
@RequiredArgsConstructor
public class SurveyInvitationController {
    
    private final SurveyInvitationService surveyInvitationService;

    @PostMapping("/{surveyId}/invite")
    public ResponseEntity<String> sendEmail(@PathVariable Long surveyId, @RequestBody SurveyInvitationRequest surveyInvitationRequest) {
        surveyInvitationService.sendEmail(surveyId, surveyInvitationRequest);
        
        // .body() ekleyerek işlemi tamamladık ve mesajımızı yazdık
        return ResponseEntity.status(201).body("Davetiye başarıyla gönderildi!");
    }
}