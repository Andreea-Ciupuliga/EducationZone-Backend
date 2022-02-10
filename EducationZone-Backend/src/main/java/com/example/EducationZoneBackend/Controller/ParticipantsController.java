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


    //*** MERGE ***
    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto>registerStudentInCourse(Long studentId,Long courseId)
    {
        participantsService.addStudentAtCourse(studentId,courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }


    //*** MERGE ***
    @GetMapping("/findAllStudentsByCourseId")
    public ResponseEntity<List<GetStudentDTO>> findAllStudentsByCourseId(Long courseId) {

        return new ResponseEntity<>(participantsService.findAllStudentsByCourseId(courseId), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping("/findCoursesByStudentId")
    public ResponseEntity<List<GetCourseDTO>> findCoursesByStudentId(Long studentId) {

        return new ResponseEntity<>(participantsService.findCoursesByStudentId(studentId), HttpStatus.OK);
    }


}
