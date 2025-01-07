package com.example.dto;

import com.example.model.Status;

public record TeststatusGet(
    int id,
    Status.status status,
    int testId,
    int studentId,
    double apptitudeMarks,
    double technicalMarks,
    double programmingMarks
) {
    
}
