package com.example.survey_app.repository;

import com.example.survey_app.entity.Survey;
import com.example.survey_app.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface SurveyRepository extends JpaRepository<Survey, Long> {
 List<Survey>  findByUser(User user);
}
