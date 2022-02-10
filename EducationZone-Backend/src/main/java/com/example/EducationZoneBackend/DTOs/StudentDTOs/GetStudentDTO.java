package com.example.EducationZoneBackend.DTOs.StudentDTOs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentDTO {

    private String firstName;

    private String lastName;

    private String email;


}
