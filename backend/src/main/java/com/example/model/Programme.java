package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Programme")
public class Programme extends Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "question_description" , nullable = false)
    private String questionDescription;

    @Column(name = "positive_mark" , nullable = false)
    @Size(min = 0 , message = "Marks Should be positive")
    private float positiveMark;

    @Column(name = "difficulty" , nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(name = "question_category" , nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id",nullable = false,referencedColumnName = "id")
    private Test test;
    
    public Programme(int id, String questionDescription,
            @Size(min = 0, message = "Marks Should be positive") float positiveMark, Difficulty difficulty,
            QuestionCategory category, Test test) {
        this.id = id;
        this.questionDescription = questionDescription;
        this.positiveMark = positiveMark;
        this.difficulty = difficulty;
        this.category = category;
        this.test = test;
    }

    public Programme() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public float getPositiveMark() {
        return positiveMark;
    }

    public void setPositiveMark(float positiveMark) {
        this.positiveMark = positiveMark;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public QuestionCategory getCategory() {
        return category;
    }

    public void setCategory(QuestionCategory category) {
        this.category = category;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((questionDescription == null) ? 0 : questionDescription.hashCode());
        result = prime * result + Float.floatToIntBits(positiveMark);
        result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((test == null) ? 0 : test.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Programme other = (Programme) obj;
        if (id != other.id)
            return false;
        if (questionDescription == null) {
            if (other.questionDescription != null)
                return false;
        } else if (!questionDescription.equals(other.questionDescription))
            return false;
        if (Float.floatToIntBits(positiveMark) != Float.floatToIntBits(other.positiveMark))
            return false;
        if (difficulty != other.difficulty)
            return false;
        if (category != other.category)
            return false;
        if (test == null) {
            if (other.test != null)
                return false;
        } else if (!test.equals(other.test))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Programme [id=" + id + ", questionDescription=" + questionDescription + ", positiveMark=" + positiveMark
                + ", difficulty=" + difficulty + ", category=" + category + ", test=" + test + "]";
    }

    

    
}
