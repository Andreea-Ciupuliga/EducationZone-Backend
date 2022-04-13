package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseAndProfessorNameDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.Service.ProfessorCourseService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professorCourse")
public class ProfessorCourseController {

    private ProfessorCourseService professorCourseService;

    @Autowired
    public ProfessorCourseController(ProfessorCourseService professorCourseService) {
        this.professorCourseService = professorCourseService;
    }

    @PostMapping("/register/{professorId}/{courseId}")
    public ResponseEntity<SuccessDto> registerProfessorToCourse(@PathVariable Long professorId, @PathVariable Long courseId) {
        professorCourseService.registerProfessorToCourse(professorId, courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getProfessorByCourseId/{courseId}")
    public ResponseEntity<Optional<GetProfessorDTO>> getProfessorByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(professorCourseService.getProfessorByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByProfessorId/{professorId}")
    public ResponseEntity<List<GetCourseAndProfessorNameDTO>> getAllCoursesByProfessorId(@PathVariable Long professorId) {

        return new ResponseEntity<>(professorCourseService.getAllCoursesByProfessorId(professorId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByProfessorUsername/{professorUsername}")
    public ResponseEntity<List<GetCourseAndProfessorNameDTO>> getAllCoursesByProfessorUsername(@PathVariable String professorUsername) {

        return new ResponseEntity<>(professorCourseService.getAllCoursesByProfessorUsername(professorUsername), HttpStatus.OK);
    }

    @DeleteMapping("/{professorId}/{courseId}")
    public ResponseEntity<SuccessDto> removeCourseProfessorRelationship(@PathVariable Long professorId, @PathVariable Long courseId) {
        professorCourseService.removeCourseProfessorRelationship(professorId, courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }
}
