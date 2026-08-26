package com.example.survey_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.survey_app.entity.SurveyInvitation;


public interface SurveyInvitationRepository extends JpaRepository<SurveyInvitation,Long>{
Optional<SurveyInvitation> findByToken(String token);
List<SurveyInvitation> findBySurveyId(Long surveyId);
}
