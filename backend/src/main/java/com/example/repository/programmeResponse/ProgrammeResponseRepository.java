package com.example.repository.programmeResponse;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.ProgrammeResponse;

public interface ProgrammeResponseRepository extends JpaRepository<ProgrammeResponse, Integer> {
    ProgrammeResponse findByStudentIdAndProgrammeId(int studentId, int programmeId);

    
}
