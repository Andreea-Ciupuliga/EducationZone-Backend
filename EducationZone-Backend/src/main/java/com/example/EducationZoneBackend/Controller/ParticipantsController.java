package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Service.ParticipantsService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantsController {

    private ParticipantsService participantsService;

    @Autowired
    public ParticipantsController(ParticipantsService participantsService) {
        this.participantsService = participantsService;
    }

    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto>registerStudentAtCourse(Long studentId,Long courseId)
    {
        participantsService.registerStudentAtCourse(studentId,courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllStudentsByCourseId/{courseId}")
    public ResponseEntity<List<GetStudentDTO>> getAllStudentsByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(participantsService.getAllStudentsByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByStudentId/{studentId}")
    public ResponseEntity<List<GetCourseDTO>> getAllCoursesByStudentId(@PathVariable Long studentId) {

        return new ResponseEntity<>(participantsService.getAllCoursesByStudentId(studentId), HttpStatus.OK);
    }

    @DeleteMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeStudentCourseRelationship(Long studentId, Long courseId) {
        participantsService.removeStudentCourseRelationship(studentId,courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }
}
