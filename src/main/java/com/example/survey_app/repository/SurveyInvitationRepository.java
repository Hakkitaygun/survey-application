package com.example.survey_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.survey_app.entity.SurveyInvitation;


public interface SurveyInvitationRepository extends JpaRepository<SurveyInvitation,Long>{
Optional<SurveyInvitation> findByToken(String token);
@Query("SELECT DISTINCT s FROM SurveyInvitation s " +
           "LEFT JOIN FETCH s.answers a " +
           "LEFT JOIN FETCH a.question " +
           "WHERE s.survey.id = :surveyId")
    List<SurveyInvitation> findBySurveyId(@Param("surveyId") Long surveyId);
}
