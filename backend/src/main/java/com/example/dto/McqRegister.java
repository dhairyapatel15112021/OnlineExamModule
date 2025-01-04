package com.example.dto;

import com.example.model.Question;

public record McqRegister(
    int id,
    int testId,
    String questionDescription,
    String option1,
    String option2,
    String option3,
    String option4,
    Question.QuestionCategory category,
    Question.Difficulty difficulty,
    float positiveMark,
    float negativeMark,
    String correctAnswer
) {
    
}
