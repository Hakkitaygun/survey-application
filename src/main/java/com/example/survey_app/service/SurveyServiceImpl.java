package com.example.survey_app.service;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.SurveyInvitation;
import com.example.survey_app.entity.User;
import com.example.survey_app.repository.SurveyInvitationRepository;
import com.example.survey_app.repository.SurveyRepository;
import com.example.survey_app.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final SurveyInvitationRepository surveyInvitationRepository;

    @Override
    public List<Survey> getAllSurveys() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
             .orElseThrow(() -> new RuntimeException("Kullanıcı Bulunamadı"));

        return surveyRepository.findByUser(user);
    }

    @Override
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

}
