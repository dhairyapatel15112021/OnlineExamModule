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
@Table(name = "mcq_response" , 
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id" , "mcq_id"})
    })
public class McqResponse {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "response" , nullable = false)
    private String response;

    @Column(name = "is_true" , nullable = false)
    private boolean isTrue = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id" , nullable = false , referencedColumnName = "id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mcq_id" , nullable = false, referencedColumnName = "id")
    private Mcq mcq;

    public McqResponse(int id, String response, boolean isTrue, Student student, Mcq mcq) {
        this.id = id;
        this.response = response;
        this.isTrue = isTrue;
        this.student = student;
        this.mcq = mcq;
    }

    public McqResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

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

    public Mcq getMcq() {
        return mcq;
    }

    public void setMcq(Mcq mcq) {
        this.mcq = mcq;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((response == null) ? 0 : response.hashCode());
        result = prime * result + (isTrue ? 1231 : 1237);
        result = prime * result + ((student == null) ? 0 : student.hashCode());
        result = prime * result + ((mcq == null) ? 0 : mcq.hashCode());
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
        McqResponse other = (McqResponse) obj;
        if (id != other.id)
            return false;
        if (response == null) {
            if (other.response != null)
                return false;
        } else if (!response.equals(other.response))
            return false;
        if (isTrue != other.isTrue)
            return false;
        if (student == null) {
            if (other.student != null)
                return false;
        } else if (!student.equals(other.student))
            return false;
        if (mcq == null) {
            if (other.mcq != null)
                return false;
        } else if (!mcq.equals(other.mcq))
            return false;
        return true;
    }

    
}
