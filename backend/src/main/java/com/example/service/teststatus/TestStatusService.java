package com.example.service.teststatus;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Teststatus;
import com.example.repository.teststatus.TestStatusRepository;

@Service
public class TestStatusService {
    
    private final TestStatusRepository testStatusRepository;

    public TestStatusService(TestStatusRepository testStatusRepository) {
        this.testStatusRepository = testStatusRepository;
    }

    public Teststatus startTest(Teststatus status){
        return testStatusRepository.save(status);
    }

    public Teststatus getTeststatus(int studentId,int Testid){
        return testStatusRepository.findByTest_idAndStudent_id(studentId, Testid);
    }

    public Teststatus saveTestStatus(Teststatus status) {
        return testStatusRepository.save(status);
    }

    public List<Teststatus> getTeststatus(int studentId) {
        return testStatusRepository.findByStudent_id(studentId);
    }
}
