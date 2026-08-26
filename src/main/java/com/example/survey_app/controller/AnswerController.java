package com.example.survey_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.survey_app.dto.AnswerSubmitRequest;
import com.example.survey_app.dto.SurveyResponse;
import com.example.survey_app.service.AnswerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/answers")
public class AnswerController {
private final AnswerService answerService;
@GetMapping("/solve")
    public ResponseEntity<SurveyResponse> getSurveyForSolve(@RequestParam String token) {
        SurveyResponse response = answerService.getSurveyByToken(token);
        return ResponseEntity.ok(response);
    }

@PostMapping("/submit")
    public ResponseEntity<String> submitAnswers(@RequestBody AnswerSubmitRequest request) {
        answerService.submitAnswers(request);
        return ResponseEntity.ok("Anket cevaplarınız başarıyla kaydedildi. Katılımınız için teşekkür ederiz!");
    }
    
}
