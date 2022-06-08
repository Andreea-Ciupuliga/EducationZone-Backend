package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.StickyNoteDTOs.GetStickyNoteDTO;
import com.example.EducationZoneBackend.DTOs.StickyNoteDTOs.RegisterStickyNoteDTO;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.StickyNote;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.StickyNoteRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StickyNoteService {

    private StickyNoteRepository stickyNoteRepository;
    private StudentRepository studentRepository;

    @Autowired
    public StickyNoteService(StickyNoteRepository stickyNoteRepository, StudentRepository studentRepository) {
        this.stickyNoteRepository = stickyNoteRepository;
        this.studentRepository = studentRepository;
    }

    @SneakyThrows
    public void createStickyNote(RegisterStickyNoteDTO registerStickyNoteDTO) {
        Student student = studentRepository.findByUsername(registerStickyNoteDTO.getStudentUsername()).orElseThrow(() -> new NotFoundException("Student not found"));
        StickyNote stickyNote = StickyNote.builder()
                .title(registerStickyNoteDTO.getTitle())
                .description(registerStickyNoteDTO.getDescription())
                .student(student)
                .build();

        stickyNoteRepository.save(stickyNote);
    }

    @SneakyThrows
    public void removeStickyNote(Long stickyNoteId) {

        StickyNote stickyNote = stickyNoteRepository.findById(stickyNoteId).orElseThrow(() -> new NotFoundException("Sticky note not found"));
        stickyNoteRepository.delete(stickyNote);
    }

    @SneakyThrows
    public List<GetStickyNoteDTO> getAllStickyNotesByStudentUsername(String studentUsername) {
        if (studentRepository.findByUsername(studentUsername).isEmpty())
            throw new NotFoundException("Student not found");

        return stickyNoteRepository.findAllStickyNotesByStudentUsername(studentUsername);
    }

}
