package com.example.repository.college;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.College;

public interface CollegeRepository extends JpaRepository<College,Integer> {
    College findByEmailId(String emailId);
}
