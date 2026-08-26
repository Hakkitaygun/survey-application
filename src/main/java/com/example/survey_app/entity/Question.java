package com.example.survey_app.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String text;

@ManyToOne
@JoinColumn(name = "survey_id")
@JsonIgnore
private Survey survey;

@OneToMany(mappedBy = "question",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Answer> answers;
}
