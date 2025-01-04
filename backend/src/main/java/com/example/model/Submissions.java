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

@Entity
@Table(name = "submissions")
public class Submissions {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programme_responses" , referencedColumnName = "id" , nullable = false)
    private ProgrammeResponse programmeResponse;

    @Column(name = "token",nullable = false)
    private String token;

    public Submissions(int id, ProgrammeResponse programmeResponse, String token) {
        this.id = id;
        this.programmeResponse = programmeResponse;
        this.token = token;
    }

    public Submissions() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProgrammeResponse getProgrammeResponse() {
        return programmeResponse;
    }

    public void setProgrammeResponse(ProgrammeResponse programmeResponse) {
        this.programmeResponse = programmeResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((programmeResponse == null) ? 0 : programmeResponse.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
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
        Submissions other = (Submissions) obj;
        if (id != other.id)
            return false;
        if (programmeResponse == null) {
            if (other.programmeResponse != null)
                return false;
        } else if (!programmeResponse.equals(other.programmeResponse))
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Submissions [id=" + id + ", programmeResponse=" + programmeResponse + ", token=" + token + "]";
    }

    

}
