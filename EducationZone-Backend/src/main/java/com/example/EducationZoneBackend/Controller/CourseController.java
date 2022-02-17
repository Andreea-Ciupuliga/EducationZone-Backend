package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.CourseDTOs.RegisterCourseDTO;
import com.example.EducationZoneBackend.Service.CourseService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerCourse(@RequestBody RegisterCourseDTO registerCourseDto)
    {
        courseService.registerCourse(registerCourseDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto>removeCourse(@PathVariable Long courseId)
    {

        courseService.removeCourse(courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetCourseDTO>> getAllCourses() {

        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @PutMapping("/{courseId}")
    @SneakyThrows
    public  ResponseEntity<SuccessDto> updateCourse(@PathVariable Long courseId, @RequestBody RegisterCourseDTO registerCourseDto)
    {
        courseService.updateCourse(courseId,registerCourseDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    @SneakyThrows
    public ResponseEntity<GetCourseDTO> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourse(courseId), HttpStatus.OK);
    }
}
