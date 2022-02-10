package com.example.EducationZoneBackend.DTOs.CourseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCourseDTO {


    private String name;

    private Long numberOfStudents;

    private String description;

    private Long year;
}
