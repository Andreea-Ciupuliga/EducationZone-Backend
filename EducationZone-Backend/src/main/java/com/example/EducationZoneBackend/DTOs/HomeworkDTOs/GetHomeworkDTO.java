package com.example.EducationZoneBackend.DTOs.HomeworkDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetHomeworkDTO {

    private String description;

    private String deadline;

    private String points;

    private Long courseId;

    private String courseName;
}
