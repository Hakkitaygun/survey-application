package com.example.survey_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.survey_app.dto.request.QuestionCreateRequest;
import com.example.survey_app.dto.response.QuestionResponse;
import com.example.survey_app.service.QuestionService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

@PostMapping("/surveys/{surveyId}/questions")
public QuestionResponse addQuestionToSurvey(@PathVariable Long surveyId,@RequestBody QuestionCreateRequest questionRequest){

    return questionService.addQuestionToSurvey(surveyId, questionRequest);
}

@GetMapping("/surveys/{surveyId}/questions")
public List<QuestionResponse> getQuestionbySurvey(@PathVariable Long surveyId) {
    return questionService.getQuestionbySurvey(surveyId);
}


}
