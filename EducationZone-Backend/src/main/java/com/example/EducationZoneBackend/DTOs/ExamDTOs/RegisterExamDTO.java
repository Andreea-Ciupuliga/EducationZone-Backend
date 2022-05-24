package com.example.EducationZoneBackend.DTOs.ExamDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterExamDTO {

    private String description;

    private String examDate;

    private String points;

    private String examRoom;

    private String examHour;

    private Long courseId;
}
