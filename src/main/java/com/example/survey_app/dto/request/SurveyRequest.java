package com.example.survey_app.dto.request;

import java.util.List;
import com.example.survey_app.dto.response.QuestionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyRequest {

    private String title;
    private String description;
    private List<QuestionResponse> questions;

}
