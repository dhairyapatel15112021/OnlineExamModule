package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String emailId;
    private String password;
    private String name;
    private String enrollmentNumber;
    private String mobileNumber;
    // BATCH ID REFERENCES TO Batch Table
    // COLLEAGE ID REFRENCES TO Clg Table
    // check valid phone number
    public Student(int id, String emailId, String password, String name, String enrollmentNumber, String mobileNumber) {
        this.id = id;
        this.emailId = emailId;
        this.password = password;
        this.name = name;
        this.enrollmentNumber = enrollmentNumber;
        this.mobileNumber = mobileNumber;
    }
    public Student() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }
    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    @Override
    public String toString() {
        return "Student [id=" + id + ", emailId=" + emailId + ", password=" + password + ", name=" + name
                + ", enrollmentNumber=" + enrollmentNumber + ", mobileNumber=" + mobileNumber + "]";
    }
    

    
}
