package com.example.EducationZoneBackend.DTO.ProfessorDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfessorDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String phone;

}
