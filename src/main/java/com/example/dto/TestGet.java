package com.example.dto;

import java.time.LocalTime;

public record TestGet(
        int id,
        String title,
        int totalApptitudeQuestion,
        int totalTechnicalQuestion,
        int totalProgrammingQuestion,
        Double passingPercentage,
        LocalTime time,
        int adminId,
        int batchId) {

}
