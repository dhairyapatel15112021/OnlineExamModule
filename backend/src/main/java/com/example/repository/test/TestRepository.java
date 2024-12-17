package com.example.repository.test;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Test;

public interface TestRepository extends JpaRepository<Test,Integer> {
    
    Test findByTitle(String title);
}
