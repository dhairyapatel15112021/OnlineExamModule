package com.example.repository.testcases;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Testcases;

public interface TestcasesRepository extends JpaRepository<Testcases , Integer> {
    
}
