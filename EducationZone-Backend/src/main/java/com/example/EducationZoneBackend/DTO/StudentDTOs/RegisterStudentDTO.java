package com.example.EducationZoneBackend.DTO.StudentDTOs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    private Long groupNumber;

    private String phone;

    private String year;

    private String department;

}
