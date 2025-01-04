package com.example.dto;

import com.example.model.Question;

public record ProgrammeRegister(
    int id,
    String questionDescription,
    float positiveMark,
    Question.Difficulty difficulty,
    Question.QuestionCategory category,
    int testId
){

}