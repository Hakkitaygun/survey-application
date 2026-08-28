package com.example.survey_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SurveyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyAppApplication.class, args);
	}

}
