package com.example.survey_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.survey_app.entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {

}
