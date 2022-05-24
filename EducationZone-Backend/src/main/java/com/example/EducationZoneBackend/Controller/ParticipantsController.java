package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseAndProfessorNameDTO;
import com.example.EducationZoneBackend.DTOs.GradeDTOs.GetGradeDTO;
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

    @PostMapping("/register/{studentId}/{courseId}")
    public ResponseEntity<SuccessDto> registerStudentAtCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        participantsService.registerStudentAtCourse(studentId, courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @PutMapping("/addGradeForStudent/{studentId}/{courseId}/{courseGrade}")
    @SneakyThrows
    public ResponseEntity<SuccessDto> addGradeForStudent(@PathVariable Long studentId, @PathVariable Long courseId, @PathVariable String courseGrade) {
        participantsService.addGradeForStudent(studentId, courseId, courseGrade);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllStudentsByCourseId/{courseId}")
    public ResponseEntity<List<GetStudentDTO>> getAllStudentsByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(participantsService.getAllStudentsByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByStudentId/{studentId}")
    public ResponseEntity<List<GetCourseAndProfessorNameDTO>> getAllCoursesByStudentId(@PathVariable Long studentId) {

        return new ResponseEntity<>(participantsService.getAllCoursesByStudentId(studentId), HttpStatus.OK);
    }

    @GetMapping("/getAllCoursesByStudentUsername/{studentUsername}")
    public ResponseEntity<List<GetCourseAndProfessorNameDTO>> getAllCoursesByStudentUsername(@PathVariable String studentUsername) {

        return new ResponseEntity<>(participantsService.getAllCoursesByStudentUsername(studentUsername), HttpStatus.OK);
    }

    @GetMapping("/getAllGradesByStudentId/{studentId}")
    public ResponseEntity<List<GetGradeDTO>> getAllGradesByStudentId(@PathVariable Long studentId) {

        return new ResponseEntity<>(participantsService.getAllGradesByStudentId(studentId), HttpStatus.OK);
    }

    @GetMapping("/getGradeByStudentIdAndCourseId/{studentId}/{courseId}")
    public ResponseEntity<String> getGradeByStudentIdAndCourseId(@PathVariable Long studentId, @PathVariable Long courseId) {

        return new ResponseEntity<>(participantsService.getGradeByStudentIdAndCourseId(studentId, courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllGradesByStudentUsername/{studentUsername}")
    public ResponseEntity<List<GetGradeDTO>> getAllGradesByStudentUsername(@PathVariable String studentUsername) {

        return new ResponseEntity<>(participantsService.getAllGradesByStudentUsername(studentUsername), HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<SuccessDto> removeStudentCourseRelationship(@PathVariable Long studentId, @PathVariable Long courseId) {
        participantsService.removeStudentCourseRelationship(studentId, courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }
}
