package com.example.repository.programme;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Programme;
import com.example.model.Question;

public interface ProgrammeRepository extends JpaRepository<Programme,Integer> {

    List<Programme> findByTest_Id(int testId);
    List<Programme> findByTest_IdAndCategory(int testId , Question.QuestionCategory Category);
}
