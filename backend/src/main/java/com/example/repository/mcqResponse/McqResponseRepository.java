package com.example.repository.mcqResponse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.McqResponse;

public interface McqResponseRepository extends JpaRepository<McqResponse,Integer> {
    
    @Query("SELECT m FROM McqResponse m JOIN Mcq q WHERE q.test.id = :testId AND m.student.id = :studentId")
    List<McqResponse> findByStudent_id(@Param("testId") int testId , @Param("studentId") int studentId);

    McqResponse findByStudentIdAndMcqId(int studentId , int mcqId);
}
