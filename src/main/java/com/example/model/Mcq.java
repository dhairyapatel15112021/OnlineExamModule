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
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "mcq")
public class Mcq extends Question {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id",nullable = false,referencedColumnName = "id")
    private Test test;

    @Column(name = "question_description" , nullable = false)
    private String questionDescription;

    @Column(name = "option_1" , nullable = false)
    private String option1;

    @Column(name = "option_2" , nullable = false)
    private String option2;

    @Column(name = "option_3" , nullable = true)
    private String option3;

    @Column(name = "option_4" , nullable = true)
    private String option4;

    @Column(name = "category" , nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Column(name = "positive_mark" , nullable = false)
    @Min(value = 0 , message = "Questions Mark Cannot be negative")
    private float positiveMark;

    @Column(name = "negative_mark" , nullable = false)
    @Min(value = 0 , message = "Questions Negative Mark Cannot be negative")
    private float negativeMark;

    @Column(name = "correct_answer" , nullable = false)
    private String correctAnswer;

    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    
    public Mcq() {
    }


    public Mcq(int id, Test test, String questionDescription, String option1, String option2, QuestionCategory category,
            @Min(value = 0, message = "Questions Mark Cannot be negative") int positiveMark,
            @Min(value = 0, message = "Questions Negative Mark Cannot be negative") int negativeMark,
            String correctAnswer, Difficulty difficulty) {
        this.id = id;
        this.test = test;
        this.questionDescription = questionDescription;
        this.option1 = option1;
        this.option2 = option2;
        this.category = category;
        this.positiveMark = positiveMark;
        this.negativeMark = negativeMark;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }


    public Mcq(int id, Test test, String questionDescription, String option1, String option2, String option3,
            String option4, QuestionCategory category,
            @Min(value = 0, message = "Questions Mark Cannot be negative") int positiveMark,
            @Min(value = 0, message = "Questions Negative Mark Cannot be negative") int negativeMark,
            String correctAnswer, Difficulty difficulty) {
        this.id = id;
        this.test = test;
        this.questionDescription = questionDescription;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.category = category;
        this.positiveMark = positiveMark;
        this.negativeMark = negativeMark;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public QuestionCategory getCategory() {
        return category;
    }

    public void setCategory(QuestionCategory category) {
        this.category = category;
    }

    public float getPositiveMark() {
        return positiveMark;
    }

    public void setPositiveMark(float positiveMark) {
        this.positiveMark = positiveMark;
    }

    public float getNegativeMark() {
        return negativeMark;
    }

    public void setNegativeMark(float negativeMark) {
        this.negativeMark = negativeMark;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        float result = 1;
        result = prime * result + id;
        result = prime * result + ((test == null) ? 0 : test.hashCode());
        result = prime * result + ((questionDescription == null) ? 0 : questionDescription.hashCode());
        result = prime * result + ((option1 == null) ? 0 : option1.hashCode());
        result = prime * result + ((option2 == null) ? 0 : option2.hashCode());
        result = prime * result + ((option3 == null) ? 0 : option3.hashCode());
        result = prime * result + ((option4 == null) ? 0 : option4.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + positiveMark;
        result = prime * result + negativeMark;
        result = prime * result + ((correctAnswer == null) ? 0 : correctAnswer.hashCode());
        result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
        return (int)result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Mcq other = (Mcq) obj;
        if (id != other.id)
            return false;
        if (test == null) {
            if (other.test != null)
                return false;
        } else if (!test.equals(other.test))
            return false;
        if (questionDescription == null) {
            if (other.questionDescription != null)
                return false;
        } else if (!questionDescription.equals(other.questionDescription))
            return false;
        if (option1 == null) {
            if (other.option1 != null)
                return false;
        } else if (!option1.equals(other.option1))
            return false;
        if (option2 == null) {
            if (other.option2 != null)
                return false;
        } else if (!option2.equals(other.option2))
            return false;
        if (option3 == null) {
            if (other.option3 != null)
                return false;
        } else if (!option3.equals(other.option3))
            return false;
        if (option4 == null) {
            if (other.option4 != null)
                return false;
        } else if (!option4.equals(other.option4))
            return false;
        if (category != other.category)
            return false;
        if (positiveMark != other.positiveMark)
            return false;
        if (negativeMark != other.negativeMark)
            return false;
        if (correctAnswer == null) {
            if (other.correctAnswer != null)
                return false;
        } else if (!correctAnswer.equals(other.correctAnswer))
            return false;
        if (difficulty != other.difficulty)
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Mcq [id=" + id + ", test=" + test + ", questionDescription=" + questionDescription + ", option1="
                + option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4 + ", category="
                + category + ", positiveMark=" + positiveMark + ", negativeMark=" + negativeMark + ", correctAnswer="
                + correctAnswer + ", difficulty=" + difficulty + "]";
    }
    
    
}
