package com.example.repository.mcqResponse;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.McqResponse;

public interface McqResponseRepository extends JpaRepository<McqResponse,Integer> {
    
    McqResponse findByStudentIdAndMcqId(int studentId , int mcqId);
}
