package com.example.survey_app.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String text;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "survey_id")
@JsonIgnore
private Survey survey;

@OneToMany(mappedBy = "question",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Answer> answers;
}
