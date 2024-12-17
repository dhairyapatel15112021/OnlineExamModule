package com.example.service.programme;

import org.springframework.stereotype.Service;

import com.example.model.Programme;
import com.example.model.Question.QuestionCategory;
import com.example.repository.programme.ProgrammeRepository;

@Service
public class ProgrammeService {
    
    private final ProgrammeRepository programmeRepository;

    public ProgrammeService(ProgrammeRepository programmeRepository) {
        this.programmeRepository = programmeRepository;
    }

    public boolean createProgramne(Programme programme){
        try{
            programmeRepository.save(programme);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public int getAllProgrammesCount(int testId){
        return programmeRepository.findByTest_IdAndCategory(testId , QuestionCategory.Programming).size();
    }
}
