package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.StickyNoteDTOs.RegisterStickyNoteDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.RegisterStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Models.StickyNote;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.StickyNoteRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StickyNoteService {

    private StickyNoteRepository stickyNoteRepository;

    @Autowired
    public StickyNoteService(StickyNoteRepository stickyNoteRepository) {
        this.stickyNoteRepository = stickyNoteRepository;
    }

    @SneakyThrows
    public void createStickyNote(RegisterStickyNoteDTO registerStickyNoteDTO) {


        StickyNote stickyNote = StickyNote.builder()
                .title(registerStickyNoteDTO.getTitle())
                .description(registerStickyNoteDTO.getDescription())
                .student(Student.builder().id(registerStickyNoteDTO.getStudentId()).build())
                .build();

        stickyNoteRepository.save(stickyNote);
    }
}
