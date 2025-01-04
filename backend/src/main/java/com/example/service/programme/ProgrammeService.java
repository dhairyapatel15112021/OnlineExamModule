package com.example.service.programme;

import java.util.List;

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

    public Programme createProgramne(Programme programme){
        try{
            return programmeRepository.save(programme);
        }
        catch(Exception e){
            return null;
        }
    }

    public int getAllProgrammesCount(int testId){
        return programmeRepository.findByTest_IdAndCategory(testId , QuestionCategory.Programming).size();
    }

    public Programme getProgramme(int programmeId){
        try{
            return programmeRepository.getReferenceById(programmeId);
        }
        catch(Exception e){
            return null;
        }
    }

    public List<Programme> getProgrammes(int testId){
        return programmeRepository.findByTest_Id(testId);
    }
}
