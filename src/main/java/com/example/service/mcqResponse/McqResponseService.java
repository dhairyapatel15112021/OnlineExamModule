package com.example.service.mcqResponse;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.McqResponse;
import com.example.repository.mcqResponse.McqResponseRepository;

@Service
public class McqResponseService {
    
    private final McqResponseRepository mcqResponseRepository;

    public McqResponseService(McqResponseRepository mcqResponseRepository) {
        this.mcqResponseRepository = mcqResponseRepository;
    }

    public boolean registerResponse(McqResponse mcqResponse){
        try{
            mcqResponseRepository.save(mcqResponse);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean isResponseExist(int studentId, int mcqId) {
        try{
            if(mcqResponseRepository.findByStudentIdAndMcqId(studentId, mcqId) == null){
                return false;
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public List<McqResponse> getAllMcqs(int studentId, int testId) {
        return mcqResponseRepository.findByStudent_id(testId, studentId);
    }
}
