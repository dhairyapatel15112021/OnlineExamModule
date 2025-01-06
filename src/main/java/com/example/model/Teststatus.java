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
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(name =  "test_status", 
uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id" , "test_id"})
})
public class Teststatus extends Status {  
    
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "test_status" , nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private status testStatus = status.NotStarted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id" , nullable = false , referencedColumnName = "id")
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id" , nullable = false, referencedColumnName = "id")
    private Student student;

    @Column(name = "apptitude_marks")
    private double apptitudeMarks = 0;

    @Column(name = "technical_marks")
    private double technicalMarks = 0;

    @Column(name = "programming_marks")
    private double programmingMarks = 0;

    public Teststatus(int id, status testStatus, Test test, Student student, double apptitudeMarks,
            double technicalMarks, double programmingMarks) {
        this.id = id;
        this.testStatus = testStatus;
        this.test = test;
        this.student = student;
        this.apptitudeMarks = apptitudeMarks;
        this.technicalMarks = technicalMarks;
        this.programmingMarks = programmingMarks;
    }

    public Teststatus() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public status getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(status testStatus) {
        this.testStatus = testStatus;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getApptitudeMarks() {
        return apptitudeMarks;
    }

    public void setApptitudeMarks(double apptitudeMarks) {
        this.apptitudeMarks = apptitudeMarks;
    }

    public double getTechnicalMarks() {
        return technicalMarks;
    }

    public void setTechnicalMarks(double technicalMarks) {
        this.technicalMarks = technicalMarks;
    }

    public double getProgrammingMarks() {
        return programmingMarks;
    }

    public void setProgrammingMarks(double programmingMarks) {
        this.programmingMarks = programmingMarks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((testStatus == null) ? 0 : testStatus.hashCode());
        result = prime * result + ((test == null) ? 0 : test.hashCode());
        result = prime * result + ((student == null) ? 0 : student.hashCode());
        long temp;
        temp = Double.doubleToLongBits(apptitudeMarks);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(technicalMarks);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(programmingMarks);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        Teststatus other = (Teststatus) obj;
        if (id != other.id)
            return false;
        if (testStatus != other.testStatus)
            return false;
        if (test == null) {
            if (other.test != null)
                return false;
        } else if (!test.equals(other.test))
            return false;
        if (student == null) {
            if (other.student != null)
                return false;
        } else if (!student.equals(other.student))
            return false;
        if (Double.doubleToLongBits(apptitudeMarks) != Double.doubleToLongBits(other.apptitudeMarks))
            return false;
        if (Double.doubleToLongBits(technicalMarks) != Double.doubleToLongBits(other.technicalMarks))
            return false;
        if (Double.doubleToLongBits(programmingMarks) != Double.doubleToLongBits(other.programmingMarks))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Teststatus [id=" + id + ", testStatus=" + testStatus + ", test=" + test + ", student=" + student
                + ", apptitudeMarks=" + apptitudeMarks + ", technicalMarks=" + technicalMarks + ", programmingMarks="
                + programmingMarks + "]";
    }

    

    
}
