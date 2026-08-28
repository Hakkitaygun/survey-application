package com.example.survey_app.service;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.survey_app.dto.request.SurveyInvitationRequest;
import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.SurveyInvitation;
import com.example.survey_app.repository.SurveyInvitationRepository;
import com.example.survey_app.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyInvitationImpl implements SurveyInvitationService {
    
    private final JavaMailSender mailSender;
    private final SurveyInvitationRepository surveyInvitationRepository;
    private final SurveyRepository surveyRepository;
    private final ModelMapper modelMapper;

    @Override
    public void sendEmail(Long surveyId, SurveyInvitationRequest surveyInvitationRequest) {
        Survey foundSurvey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Kayıtlı Anket Bulunamadı"));
        
        SurveyInvitation surveyInvitation = modelMapper.map(surveyInvitationRequest, SurveyInvitation.class);
        surveyInvitation.setSurvey(foundSurvey);
        surveyInvitation.setToken(UUID.randomUUID().toString());
        surveyInvitationRepository.save(surveyInvitation);
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(surveyInvitationRequest.getReceiverEmail());
            message.setSubject("YENİ BİR ANKET DAVETİNİZ VAR");
            message.setText("Lütfen anketi çözmek için tıklayın: http://localhost:8080/solve.html?token=" + surveyInvitation.getToken());
            
            mailSender.send(message);
            System.out.println("E-posta başarıyla gönderildi: " + surveyInvitationRequest.getReceiverEmail());
            
        } catch (Exception e) {
            // Hatanın tam detayı konsola yazılacak
            System.err.println("MAİL GÖNDERME HATASI: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Mail gönderilemedi: " + e.getMessage());
        }
    }
}
