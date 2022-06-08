package com.example.EducationZoneBackend.DTOs.StickyNoteDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStickyNoteDTO {

    private Long id;

    private String title;

    private String description;

}
