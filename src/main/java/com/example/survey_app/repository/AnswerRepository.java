package com.example.survey_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.survey_app.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
List<Answer> findBySurveyInvitationId(Long surveyInvitationId);
}
