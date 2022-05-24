package com.example.EducationZoneBackend.DTOs.ExamDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetExamDTO {

    private Long id;

    private String courseName;

    private String description;

    private String examDate;

    private String points;

    private String examRoom;

    private String examHour;

    private Long courseId;
}
