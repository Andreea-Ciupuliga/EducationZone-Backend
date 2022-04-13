package com.example.EducationZoneBackend.DTOs.CourseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseAndProfessorNameDTO {
    private Long id;

    private String name;

    private Long numberOfStudents;

    private String description;

    private String year;

    private String semester;

    private String professorName;
}
