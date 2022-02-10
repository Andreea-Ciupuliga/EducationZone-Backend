package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.Service.ProfessorCourseService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professorCourse")
public class ProfessorCourseController {

    private ProfessorCourseService professorCourseService;

    @Autowired
    public ProfessorCourseController(ProfessorCourseService professorCourseService) {
        this.professorCourseService = professorCourseService;
    }

    //*** MERGE ***
    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerProfessorInCourse(Long professorId, Long courseId)
    {
        professorCourseService.addProfessorAtCourse(professorId,courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping("/findAllProfessorsByCourseId")
    public ResponseEntity<List<GetProfessorDTO>> findAllProfessorsByCourseId(Long courseId) {

        return new ResponseEntity<>(professorCourseService.findAllProfessorsByCourseId(courseId), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping("/findCoursesByProfessorId")
    public ResponseEntity<List<GetCourseDTO>> findCoursesByProfessorId(Long professorId) {

        return new ResponseEntity<>(professorCourseService.findCoursesByProfessorId(professorId), HttpStatus.OK);
    }


}
