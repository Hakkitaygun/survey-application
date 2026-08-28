package com.example.survey_app.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponse {

    private Long id;
    private String title;
    private String description;
    private List<QuestionResponse> questions;
}
