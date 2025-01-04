package com.example.service.submissions;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Response;
import com.example.model.Submissions;
import com.example.repository.submissions.SubmissionsRepository;

@Service
public class SubmissionsService {
    
    private final SubmissionsRepository submissionsRepository;

    public SubmissionsService(SubmissionsRepository submissionsRepository) {
        this.submissionsRepository = submissionsRepository;
    }

    public boolean createSubmission(List<Submissions> submissions){
        try{
            submissionsRepository.saveAll(submissions);
            return true;
        }
        catch(Exception e){
            System.out.println("in the submission service" + e.getMessage());
            return false;
        }
    }

    public List<Response> getTokens(int programId){
        try{
            return submissionsRepository.findAllTokens(programId);
        }   
        catch(Exception e){
            System.out.println(e.getMessage());
            return List.of();
        }
       
    }
}

