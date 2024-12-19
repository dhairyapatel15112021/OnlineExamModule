package com.example.service.testcases;

import org.springframework.stereotype.Service;

import com.example.model.Programme;
import com.example.model.Testcases;
import com.example.repository.testcases.TestcasesRepository;

@Service
public class TestcasesService {
    
    private final TestcasesRepository testcasesRepository;

    public TestcasesService(TestcasesRepository testcasesRepository) {
        this.testcasesRepository = testcasesRepository;
    }

    public boolean createTestcases(Testcases testcase){
        try{
            testcasesRepository.save(testcase);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
