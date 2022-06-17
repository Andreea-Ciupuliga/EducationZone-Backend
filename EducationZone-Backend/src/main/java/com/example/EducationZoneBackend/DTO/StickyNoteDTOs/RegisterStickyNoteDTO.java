package com.example.EducationZoneBackend.DTO.StickyNoteDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStickyNoteDTO {

    private String title;

    private String description;

    private String studentUsername;
}
