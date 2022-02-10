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

    //*** MERGE ***
    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerCourse(@RequestBody RegisterCourseDTO registerCourseDto)
    {
        courseService.registerCourse(registerCourseDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @DeleteMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto>removeCourse(@RequestParam Long courseId)
    {

        courseService.removeCourse(courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping("/showCourses")
    public ResponseEntity<List<GetCourseDTO>> getAllCourses() {

        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    //*** MERGE ***
    @PutMapping()
    @SneakyThrows
    public  ResponseEntity<SuccessDto> putCourse(@RequestParam Long courseId, @RequestBody RegisterCourseDTO registerCourseDto)
    {
        courseService.putCourse(courseId,registerCourseDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }
}
