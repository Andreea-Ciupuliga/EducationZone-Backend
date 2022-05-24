package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseAndProfessorNameDTO;
import com.example.EducationZoneBackend.DTOs.CourseDTOs.RegisterCourseDTO;
import com.example.EducationZoneBackend.Service.CourseService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessDto> registerCourse(@RequestBody RegisterCourseDTO registerCourseDto) {
        courseService.registerCourse(registerCourseDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<SuccessDto> removeCourse(@PathVariable Long courseId) {
        courseService.removeCourse(courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetCourseAndProfessorNameDTO>> getAllCourses() {

        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<SuccessDto> updateCourse(@PathVariable Long courseId, @RequestBody RegisterCourseDTO registerCourseDto) {
        courseService.updateCourse(courseId, registerCourseDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<GetCourseAndProfessorNameDTO> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByProfessorId/{professorId}")
    public ResponseEntity<List<GetCourseAndProfessorNameDTO>> getAllCoursesByProfessorId(@PathVariable Long professorId) {

        return new ResponseEntity<>(courseService.getAllCoursesByProfessorId(professorId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByProfessorUsername/{professorUsername}")
    public ResponseEntity<List<GetCourseAndProfessorNameDTO>> getAllCoursesByProfessorUsername(@PathVariable String professorUsername) {

        return new ResponseEntity<>(courseService.getAllCoursesByProfessorUsername(professorUsername), HttpStatus.OK);
    }

    @GetMapping("/getAllByName/{courseName}")
    public ResponseEntity<List<GetCourseAndProfessorNameDTO>> getAllCoursesByName(@PathVariable String courseName) {

        return new ResponseEntity<>(courseService.getAllCoursesByName(courseName), HttpStatus.OK);
    }
}
