package com.example.EducationZoneBackend.DTOs.ProfessorDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfessorDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String phone;

}
