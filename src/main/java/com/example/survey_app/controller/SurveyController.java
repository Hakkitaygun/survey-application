package com.example.survey_app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.survey_app.dto.SurveyResponse;
import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.SurveyInvitation;
import com.example.survey_app.service.SurveyService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {

private final SurveyService surveyService;

@GetMapping
public ResponseEntity<List<SurveyResponse>> getAllSurvey(){
    List<SurveyResponse> surveys = surveyService.getAllSurveys();
    return ResponseEntity.ok(surveys);
}
@PostMapping
public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey){
    Survey createdSurvey = surveyService.createSurvey(survey);
    return ResponseEntity.status(201).body(createdSurvey);
}
@GetMapping("/{surveyId}/results")
public ResponseEntity<List<SurveyInvitation>> getSurveyResult(@PathVariable Long surveyId) {
    List<SurveyInvitation> results = surveyService.getSurveyResult(surveyId);
    return ResponseEntity.ok(results);
}

}