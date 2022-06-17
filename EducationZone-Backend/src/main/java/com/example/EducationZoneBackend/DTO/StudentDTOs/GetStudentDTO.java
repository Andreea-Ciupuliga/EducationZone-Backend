package com.example.EducationZoneBackend.DTO.StudentDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private Long groupNumber;

    private String phone;

    private String year;

    private String department;

}
