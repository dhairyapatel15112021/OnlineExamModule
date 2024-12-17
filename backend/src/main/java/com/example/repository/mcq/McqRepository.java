package com.example.repository.mcq;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Mcq;
import com.example.model.Question;

public interface McqRepository extends JpaRepository<Mcq,Integer> {
    
    List<Mcq> findByTest_Id(int testId);
    List<Mcq> findByTest_IdAndCategory(int testId , Question.QuestionCategory Category);
}
