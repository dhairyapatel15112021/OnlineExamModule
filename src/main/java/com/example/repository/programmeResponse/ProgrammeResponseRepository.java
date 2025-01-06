package com.example.repository.programmeResponse;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.ProgrammeResponse;

public interface ProgrammeResponseRepository extends JpaRepository<ProgrammeResponse, Integer> {
    
    @Query("SELECT m FROM ProgrammeResponse m JOIN Programme q WHERE q.test.id = :testId AND m.student.id = :studentId")
    List<ProgrammeResponse> findByStudent_id(@Param("testId") int testId , @Param("studentId") int studentId);

    ProgrammeResponse findByStudentIdAndProgrammeId(int studentId, int programmeId);
}
