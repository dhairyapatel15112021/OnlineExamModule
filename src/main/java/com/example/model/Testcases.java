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
@Table(name = "testcases")
public class Testcases {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programme_id" , nullable = false, referencedColumnName = "id")
    private Programme programme;

    @Column(name = "input" , nullable = false)
    private String input;

    @Column(name = "output" , nullable = false)
    private String output;

    public Testcases(int id, Programme programme, String input, String output) {
        this.id = id;
        this.programme = programme;
        this.input = input;
        this.output = output;
    }

    public Testcases() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Testcases [id=" + id + ", programme=" + programme + ", input=" + input + ", output=" + output + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((programme == null) ? 0 : programme.hashCode());
        result = prime * result + ((input == null) ? 0 : input.hashCode());
        result = prime * result + ((output == null) ? 0 : output.hashCode());
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
        Testcases other = (Testcases) obj;
        if (id != other.id)
            return false;
        if (programme == null) {
            if (other.programme != null)
                return false;
        } else if (!programme.equals(other.programme))
            return false;
        if (input == null) {
            if (other.input != null)
                return false;
        } else if (!input.equals(other.input))
            return false;
        if (output == null) {
            if (other.output != null)
                return false;
        } else if (!output.equals(other.output))
            return false;
        return true;
    }

    
    
}
