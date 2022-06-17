package com.example.EducationZoneBackend.DTO.CourseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCourseDTO {


    private String name;

    private String description;

    private String year;

    private String semester;

    private Long professorId;
}
