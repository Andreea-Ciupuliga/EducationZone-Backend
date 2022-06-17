package com.example.EducationZoneBackend.DTO.GradeDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGradeDTO {

    private String courseName;

    private Long courseGrade;
}
