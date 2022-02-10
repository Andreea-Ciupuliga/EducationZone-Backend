package com.example.EducationZoneBackend.DTOs.ExamDTOs;

import com.example.EducationZoneBackend.Models.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterExamDTO {

    private String description;

    private String examDate;

    private String points;

    private Long courseId;
}
