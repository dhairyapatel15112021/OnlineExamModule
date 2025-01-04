package com.example.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "programme_response", 
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id" , "programme_id"})
    })
public class ProgrammeResponse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // @Column(name = "response" , nullable = false , columnDefinition = "TEXT")
    // @Lob
    // private String response;

    @Column(name = "is_true" , nullable = false)
    private boolean isTrue = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id"  , nullable = false, referencedColumnName = "id" )
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programme_id" , nullable = false , referencedColumnName = "id")
    private Programme programme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id" , nullable = false , referencedColumnName = "language_id")
    private Languages languages;

    public ProgrammeResponse(int id, boolean isTrue, Student student, Programme programme,
            Languages languages) {
        this.id = id;
        // this.response = response;
        this.isTrue = isTrue;
        this.student = student;
        this.programme = programme;
        this.languages = languages;
        // this.submissionId = submissionId;
    }

    public ProgrammeResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // public String getResponse() {
    //     return response;
    // }

    // public void setResponse(String response) {
    //     this.response = response;
    // }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }

    // public String getSubmissionId() {
    //     return submissionId;
    // }

    // public void setSubmissionId(String submissionId) {
    //     this.submissionId = submissionId;
    // }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        // result = prime * result + ((response == null) ? 0 : response.hashCode());
        result = prime * result + (isTrue ? 1231 : 1237);
        result = prime * result + ((student == null) ? 0 : student.hashCode());
        result = prime * result + ((programme == null) ? 0 : programme.hashCode());
        result = prime * result + ((languages == null) ? 0 : languages.hashCode());
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
        ProgrammeResponse other = (ProgrammeResponse) obj;
        if (id != other.id)
            return false;
        // if (response == null) {
        //     if (other.response != null)
        //         return false;
        // } else if (!response.equals(other.response))
        //     return false;
        if (isTrue != other.isTrue)
            return false;
        if (student == null) {
            if (other.student != null)
                return false;
        } else if (!student.equals(other.student))
            return false;
        if (programme == null) {
            if (other.programme != null)
                return false;
        } else if (!programme.equals(other.programme))
            return false;
        if (languages == null) {
            if (other.languages != null)
                return false;
        } else if (!languages.equals(other.languages))
            return false;
        // if (submissionId == null) {
        //     if (other.submissionId != null)
        //         return false;
        // } else if (!submissionId.equals(other.submissionId))
        //     return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProgrammeResponse [id=" + id  + ", isTrue=" + isTrue + ", student=" + student
                + ", programme=" + programme + ", languages=" + languages +  "]";
    }

   
    
    
}
