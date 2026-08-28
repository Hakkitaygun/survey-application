package com.example.survey_app.service.impl;

import com.example.survey_app.service.SurveyService;

import jakarta.transaction.Transactional;

import com.example.survey_app.controller.QuestionController;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.survey_app.dto.request.SurveyRequest;
import com.example.survey_app.dto.response.QuestionResponse;
import com.example.survey_app.dto.response.SurveyResponse;
import com.example.survey_app.entity.Question;
import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.SurveyInvitation;
import com.example.survey_app.entity.User;
import com.example.survey_app.repository.SurveyInvitationRepository;
import com.example.survey_app.repository.SurveyRepository;
import com.example.survey_app.repository.UserRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.modelmapper.ModelMapper;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final SurveyInvitationRepository surveyInvitationRepository;
    private final ModelMapper modelMapper;


    @Override
    @Cacheable(value = "user_surveys", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public List<SurveyResponse> getAllSurveys() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
             .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

        List<Survey> surveys = surveyRepository.findByUserWithQuestions(user);
        return surveys.stream()
                .map(survey -> modelMapper.map(survey, SurveyResponse.class))
                .toList();
    }

    @Override
    @CacheEvict(value = "user_surveys", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()")
    public Survey createSurvey(Survey survey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));
        survey.setUser(currentUser);
        return surveyRepository.save(survey);
    }

    public List<SurveyInvitation> getSurveyResult(Long surveyId) {
    List<SurveyInvitation> results = surveyInvitationRepository.findBySurveyId(surveyId);

    return results;
}

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "user_surveys", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()"),
        @CacheEvict(value = "survey_details", key = "#surveyId")
    })
    public SurveyResponse updateSurvey(Long surveyId, SurveyRequest surveyRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
        .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

        Survey survey = surveyRepository.findById(surveyId)
        .orElseThrow(()-> new RuntimeException("Anket Bulunamadı"));

        if(!survey.getUser().getUsername().equals(currentUsername)){
            throw new RuntimeException("Bu Anketi Düzenlemeye Yetkiniz Yok");
        }

        survey.setTitle(surveyRequest.getTitle());
        survey.setDescription(surveyRequest.getDescription());

        survey.getQuestions().clear();

        if(surveyRequest.getQuestions() != null){
            for (QuestionResponse qDTO : surveyRequest.getQuestions()) {
                Question question = new Question();
                question.setText(qDTO.getText());
                question.setSurvey(survey);
                survey.getQuestions().add(question);
                
            }
        }


        

        Survey updatedSurvey = surveyRepository.save(survey);
        return modelMapper.map(updatedSurvey, SurveyResponse.class);  
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "user_surveys", key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getName()"),
        @CacheEvict(value = "survey_details", key = "#surveyId")
    })
    public Survey deleteSurvey(Long surveyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User currentUser = userRepository.findByUsername(currentUsername)
        .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

        Survey deletedSurvey = surveyRepository.findById(surveyId)
        .orElseThrow(() -> new RuntimeException("Anket Bulunamadı"));
        
        if(!deletedSurvey.getUser().getUsername().equals(currentUser.getUsername())){
           throw new RuntimeException("Bu Anketi Silme Yetkiniz Yok");
        }

        surveyRepository.deleteById(surveyId);


        return deletedSurvey;
    }


}
