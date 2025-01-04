package com.example.repository.testcases;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Testcases;

public interface TestcasesRepository extends JpaRepository<Testcases , Integer> {
    
    List<Testcases> findAllByProgramme_Id(int id);
}
