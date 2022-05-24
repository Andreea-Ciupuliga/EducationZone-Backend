package com.example.EducationZoneBackend.DTOs.StickyNoteDTOs;

import com.example.EducationZoneBackend.Models.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStickyNoteDTO {

    private String title;

    private String description;

    private Long studentId;
}
