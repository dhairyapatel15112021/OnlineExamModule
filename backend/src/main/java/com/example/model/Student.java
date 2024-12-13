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
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email_id" , nullable = false , unique = true)
    private String emailId;

    @Column(name = "password" , nullable = false)
    private String password;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "enrollment_number" , nullable = false)
    private String enrollmentNumber;

    @Column(name = "mobile_number" , nullable = false , length = 10)
    @Pattern(regexp = "\\d{10}" , message = "Mobile Number should be length of 10")
    private String mobileNumber;

    
    // COLLEAGE ID REFRENCES TO Clg Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clg_id" , referencedColumnName = "id")
    private College clg;

    // BATCH ID REFERENCES TO Batch Table
    @ManyToOne(fetch = FetchType.LAZY) // if you don’t always need related data to be fetched eagerly.
    @JoinColumn(name = "batch_id" , referencedColumnName = "id")
    private Batch batch;

    public Student(int id, String emailId, String password, String name, String enrollmentNumber,String mobileNumber,
            College clg, Batch batch) {
        this.id = id;
        this.emailId = emailId;
        this.password = password;
        this.name = name;
        this.enrollmentNumber = enrollmentNumber;
        this.mobileNumber = mobileNumber;
        this.clg = clg;
        this.batch = batch;
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

    public College getClg() {
        return clg;
    }

    public void setClg(College clg) {
        this.clg = clg;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((enrollmentNumber == null) ? 0 : enrollmentNumber.hashCode());
        result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
        result = prime * result + ((clg == null) ? 0 : clg.hashCode());
        result = prime * result + ((batch == null) ? 0 : batch.hashCode());
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
        Student other = (Student) obj;
        if (id != other.id)
            return false;
        if (emailId == null) {
            if (other.emailId != null)
                return false;
        } else if (!emailId.equals(other.emailId))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (enrollmentNumber == null) {
            if (other.enrollmentNumber != null)
                return false;
        } else if (!enrollmentNumber.equals(other.enrollmentNumber))
            return false;
        if (mobileNumber == null) {
            if (other.mobileNumber != null)
                return false;
        } else if (!mobileNumber.equals(other.mobileNumber))
            return false;
        if (clg == null) {
            if (other.clg != null)
                return false;
        } else if (!clg.equals(other.clg))
            return false;
        if (batch == null) {
            if (other.batch != null)
                return false;
        } else if (!batch.equals(other.batch))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", emailId=" + emailId + ", password=" + password + ", name=" + name
                + ", enrollmentNumber=" + enrollmentNumber + ", mobileNumber=" + mobileNumber + ", clg=" + clg
                + ", batch=" + batch + "]";
    }

   
    
}
