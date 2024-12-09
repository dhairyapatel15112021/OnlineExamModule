package com.example.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "test")
public class Test {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title" , nullable = false)
    private String title;

    @Min(value = 0 , message = "Question Number Should be Positive")
    @Column(name = "total_apptitude_question" , nullable = false)
    private int totalApptitudeQuestion;

    @Min(value = 0 , message = "Question Number Should be Positive")
    @Column(name = "total_technical_question" , nullable = false)
    private int totalTechnicalQuestion;

    @Min(value = 0 , message = "Question Number Should be Positive")
    @Column(name = "total_programming_question" , nullable = false)
    private int totalProgrammingQuestion;

    @Column(name = "time")
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id" , referencedColumnName = "id")
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id" , referencedColumnName = "id")
    private Admin admin;

    public Test(int id, String title, int totalApptitudeQuestion, int totalTechnicalQuestion,
            int totalProgrammingQuestion, LocalTime time, Batch batch, Admin admin) {
        this.id = id;
        this.title = title;
        this.totalApptitudeQuestion = totalApptitudeQuestion;
        this.totalTechnicalQuestion = totalTechnicalQuestion;
        this.totalProgrammingQuestion = totalProgrammingQuestion;
        this.time = time;
        this.batch = batch;
        this.admin = admin;
    }

    public Test() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + totalApptitudeQuestion;
        result = prime * result + totalTechnicalQuestion;
        result = prime * result + totalProgrammingQuestion;
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((batch == null) ? 0 : batch.hashCode());
        result = prime * result + ((admin == null) ? 0 : admin.hashCode());
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
        Test other = (Test) obj;
        if (id != other.id)
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (totalApptitudeQuestion != other.totalApptitudeQuestion)
            return false;
        if (totalTechnicalQuestion != other.totalTechnicalQuestion)
            return false;
        if (totalProgrammingQuestion != other.totalProgrammingQuestion)
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (batch == null) {
            if (other.batch != null)
                return false;
        } else if (!batch.equals(other.batch))
            return false;
        if (admin == null) {
            if (other.admin != null)
                return false;
        } else if (!admin.equals(other.admin))
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalApptitudeQuestion() {
        return totalApptitudeQuestion;
    }

    public void setTotalApptitudeQuestion(int totalApptitudeQuestion) {
        this.totalApptitudeQuestion = totalApptitudeQuestion;
    }

    public int getTotalTechnicalQuestion() {
        return totalTechnicalQuestion;
    }

    public void setTotalTechnicalQuestion(int totalTechnicalQuestion) {
        this.totalTechnicalQuestion = totalTechnicalQuestion;
    }

    public int getTotalProgrammingQuestion() {
        return totalProgrammingQuestion;
    }

    public void setTotalProgrammingQuestion(int totalProgrammingQuestion) {
        this.totalProgrammingQuestion = totalProgrammingQuestion;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    
}