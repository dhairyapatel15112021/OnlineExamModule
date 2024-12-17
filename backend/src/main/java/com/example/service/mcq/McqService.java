package com.example.service.mcq;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Mcq;
import com.example.model.Question;
import com.example.repository.mcq.McqRepository;

@Service
public class McqService {
    
    private final McqRepository mcqRepository;

    public McqService(McqRepository mcqRepository) {
        this.mcqRepository = mcqRepository;
    }

    public List<Mcq> getAllMcqs(int id){
        return mcqRepository.findByTest_Id(id);
    }

    @Transactional
    public boolean createMcq(Mcq mcq){
        try{
            mcqRepository.save(mcq);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public int getAllMcqs(int testId , Question.QuestionCategory category) {
        return mcqRepository.findByTest_IdAndCategory(testId, category).size();
    }

}
