package com.example.service.test;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.model.Admin;
import com.example.model.Batch;
import com.example.model.Test;
import com.example.repository.admin.AdminRepository;
import com.example.repository.batch.BatchRepository;
import com.example.repository.test.TestRepository;

@Service
public class TestService {
    
    private final TestRepository testRepository;
    private final AdminRepository adminRepository;
    private final BatchRepository batchRepository;

    public TestService(TestRepository testRepository , AdminRepository adminRepository , BatchRepository batchRepository) {
        this.testRepository = testRepository;
        this.adminRepository = adminRepository;
        this.batchRepository = batchRepository;
    }
    
    public Test checkTest(String testTitle){
        return testRepository.findByTitle(testTitle);
    }

    public Boolean createTest(Test test , int batchId, int adminId){
        try{
            if(checkTest(test.getTitle()) != null){
                throw new Exception("Exam Exist With same title");
            }
            Admin admin = adminRepository.getReferenceById(adminId);
            Batch batch = batchRepository.getReferenceById(batchId);
            test.setAdmin(admin);
            test.setBatch(batch);
            testRepository.save(test);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public Test findTotalQuestion(int testId) {
        try{            
            Test test = testRepository.getReferenceById(testId);
            return test;
        }
        catch(Exception e){
            return null;
        }
    }

}
