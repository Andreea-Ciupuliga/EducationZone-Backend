package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.Service.ProfessorCourseService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professorCourse")
public class ProfessorCourseController {

    private ProfessorCourseService professorCourseService;

    @Autowired
    public ProfessorCourseController(ProfessorCourseService professorCourseService) {
        this.professorCourseService = professorCourseService;
    }

    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerProfessorToCourse(Long professorId, Long courseId)
    {
        professorCourseService.registerProfessorToCourse(professorId,courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllProfessorsByCourseId")
    public ResponseEntity<List<GetProfessorDTO>> getAllProfessorsByCourseId(Long courseId) {

        return new ResponseEntity<>(professorCourseService.getAllProfessorsByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByProfessorId")
    public ResponseEntity<List<GetCourseDTO>> getAllCoursesByProfessorId(Long professorId) {

        return new ResponseEntity<>(professorCourseService.getAllCoursesByProfessorId(professorId), HttpStatus.OK);
    }

    @DeleteMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeCourseProfessorRelationship(Long professorId, Long courseId) {
        professorCourseService.removeCourseProfessorRelationship(professorId,courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }
}
