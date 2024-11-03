package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class College {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String clgEmailId;
    private String clgName;
    private String clgAddress;
    private String clgContactNumber;
    public College(int id, String clgEmailId, String clgName, String clgAddress, String clgContactNumber) {
        this.id = id;
        this.clgEmailId = clgEmailId;
        this.clgName = clgName;
        this.clgAddress = clgAddress;
        this.clgContactNumber = clgContactNumber;
    }
    public College() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getClgEmailId() {
        return clgEmailId;
    }
    public void setClgEmailId(String clgEmailId) {
        this.clgEmailId = clgEmailId;
    }
    public String getClgName() {
        return clgName;
    }
    public void setClgName(String clgName) {
        this.clgName = clgName;
    }
    public String getClgAddress() {
        return clgAddress;
    }
    public void setClgAddress(String clgAddress) {
        this.clgAddress = clgAddress;
    }
    public String getClgContactNumber() {
        return clgContactNumber;
    }
    public void setClgContactNumber(String clgContactNumber) {
        this.clgContactNumber = clgContactNumber;
    }
    @Override
    public String toString() {
        return "College [id=" + id + ", clgEmailId=" + clgEmailId + ", clgName=" + clgName + ", clgAddress="
                + clgAddress + ", clgContactNumber=" + clgContactNumber + "]";
    }
    
    
}
