package com.example.EducationZoneBackend.DTOs.StudentDTOs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStudentDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String username;

    private String studentSet;

    private String studentGroup;

    private String phone;

    private String year;
}
