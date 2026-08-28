package com.example.survey_app.service.impl;

import com.example.survey_app.service.AnswerService;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.survey_app.dto.request.AnswerSubmitRequest;
import com.example.survey_app.dto.response.SurveyResponse;
import com.example.survey_app.entity.Answer;
import com.example.survey_app.entity.Question;
import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.SurveyInvitation;
import com.example.survey_app.repository.AnswerRepository;
import com.example.survey_app.repository.QuestionRepository;
import com.example.survey_app.repository.SurveyInvitationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final SurveyInvitationRepository surveyInvitationRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    @Override
    public SurveyResponse getSurveyByToken(String token) {
        SurveyInvitation invitation = surveyInvitationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Geçersiz davetiye token'ı!"));

        if (invitation.isCompleted()) {
            throw new RuntimeException("Bu anket daha önce doldurulmuş!");
        }

        Survey survey = invitation.getSurvey();
        return modelMapper.map(survey, SurveyResponse.class);
    }

    @Override
    @Transactional 
    public void submitAnswers(AnswerSubmitRequest request) {
        SurveyInvitation invitation = surveyInvitationRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Geçersiz davetiye token'ı!"));

        if (invitation.isCompleted()) {
            throw new RuntimeException("Bu anket zaten daha önce tamamlanmış!");
        }

        List<Answer> answers = request.getAnswers().stream().map(dto -> {
            Question question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Soru bulunamadı, ID: " + dto.getQuestionId()));

            Answer answer = new Answer();
            answer.setAnswerText(dto.getAnswerText());
            answer.setQuestion(question);
            answer.setSurveyInvitation(invitation);
            return answer;
        }).collect(Collectors.toList());

        answerRepository.saveAll(answers);

        invitation.setCompleted(true);
        surveyInvitationRepository.save(invitation);
    }
}
