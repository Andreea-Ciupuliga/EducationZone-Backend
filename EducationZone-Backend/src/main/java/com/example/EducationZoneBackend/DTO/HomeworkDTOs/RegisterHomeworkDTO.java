package com.example.EducationZoneBackend.DTO.HomeworkDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterHomeworkDTO {

    private String description;

    private String deadline;

    private Long points;

    private Long courseId;
}
