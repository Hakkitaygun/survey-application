package com.example.survey_app.service.impl;

import com.example.survey_app.service.QuestionService;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.survey_app.repository.QuestionRepository;
import com.example.survey_app.repository.SurveyRepository;
import com.example.survey_app.entity.Survey;
import com.example.survey_app.dto.request.QuestionCreateRequest;
import com.example.survey_app.dto.response.QuestionResponse;
import com.example.survey_app.entity.Question;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;
    private final ModelMapper modelMapper;

    @Override
    public QuestionResponse addQuestionToSurvey(Long surveyId, QuestionCreateRequest questionRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Survey foundSurvey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Anket Bulunamadı"));
        if (!foundSurvey.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("Güvenlik İhlali: Bu ankete soru ekleme yetkiniz yok!");
        }
        Question question = modelMapper.map(questionRequest, Question.class);
        question.setSurvey(foundSurvey);
        Question savedQuestion =questionRepository.save(question);


        return modelMapper.map(savedQuestion, QuestionResponse.class);
    }

    @Override
    public List<QuestionResponse> getQuestionbySurvey(Long surveyId) {
        Survey foundSurvey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip anket bulunamadı: " + surveyId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (!foundSurvey.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("Güvenlik İhlali: Bu anketin sorularını görüntüleme yetkiniz yok!");
        }


        return foundSurvey.getQuestions().stream().map(question -> modelMapper.map(question, QuestionResponse.class)).toList();
    }
}