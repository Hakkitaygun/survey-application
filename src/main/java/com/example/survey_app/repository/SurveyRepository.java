package com.example.survey_app.repository;

import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface SurveyRepository extends JpaRepository<Survey, Long> {
    @Query("SELECT DISTINCT s FROM Survey s LEFT JOIN FETCH s.questions WHERE s.user = :user")
    List<Survey> findByUserWithQuestions(@Param("user") User user);
}
