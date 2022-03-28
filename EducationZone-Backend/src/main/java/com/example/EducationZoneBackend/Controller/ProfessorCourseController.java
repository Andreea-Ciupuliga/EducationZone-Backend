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

    @PostMapping("/register/{professorId}/{courseId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerProfessorToCourse(@PathVariable Long professorId,@PathVariable Long courseId)
    {
        professorCourseService.registerProfessorToCourse(professorId,courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllProfessorsByCourseId/{courseId}")
    public ResponseEntity<List<GetProfessorDTO>> getAllProfessorsByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(professorCourseService.getAllProfessorsByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByProfessorId/{professorId}")
    public ResponseEntity<List<GetCourseDTO>> getAllCoursesByProfessorId(@PathVariable Long professorId) {

        return new ResponseEntity<>(professorCourseService.getAllCoursesByProfessorId(professorId), HttpStatus.OK);
    }

    @DeleteMapping("/{professorId}/{courseId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeCourseProfessorRelationship(@PathVariable Long professorId,@PathVariable Long courseId) {
        professorCourseService.removeCourseProfessorRelationship(professorId,courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }
}
