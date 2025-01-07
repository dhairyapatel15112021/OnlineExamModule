package com.example.service.testcases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Testcases;
import com.example.repository.testcases.TestcasesRepository;

@Service
public class TestcasesService {
    
    private final TestcasesRepository testcasesRepository;

    public TestcasesService(TestcasesRepository testcasesRepository) {
        this.testcasesRepository = testcasesRepository;
    }

    public List<Testcases> getTestcases(int id){
        return testcasesRepository.findAllByProgramme_Id(id);
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

    public List<Testcases> getTestcases() {
      return testcasesRepository.findAll();
    }
}
