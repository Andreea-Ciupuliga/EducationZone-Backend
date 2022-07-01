package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseAndProfessorNameDTO;
import com.example.EducationZoneBackend.DTO.GradeDTOs.GetGradeDTO;
import com.example.EducationZoneBackend.DTO.StudentDTOs.GetStudentAndGradeDTO;
import com.example.EducationZoneBackend.DTO.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Service.ParticipantsService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
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

    @PostMapping("/registerGroupAtCourse/{groupNumber}/{courseId}")
    public ResponseEntity<SuccessDto> registerGroupAtCourse(@PathVariable Long groupNumber, @PathVariable Long courseId) {
        participantsService.registerGroupAtCourse(groupNumber, courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @PutMapping("/addGradeForStudent/{studentId}/{courseId}/{courseGrade}")
    public ResponseEntity<SuccessDto> addGradeForStudent(@PathVariable Long studentId, @PathVariable Long courseId, @PathVariable Long courseGrade) {
        participantsService.addGradeForStudent(studentId, courseId, courseGrade);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllStudentsByCourseId/{courseId}")
    public ResponseEntity<List<GetStudentDTO>> getAllStudentsByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(participantsService.getAllStudentsByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllStudentsAndGradesByCourseId/{courseId}")
    public ResponseEntity<List<GetStudentAndGradeDTO>> getAllStudentsAndGradesByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(participantsService.getAllStudentsAndGradesByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllStudentsAndGradesByCourseIdAndStudentName/{courseId}/{studentName}")
    public ResponseEntity<List<GetStudentAndGradeDTO>> getAllStudentsAndGradesByCourseIdAndStudentName(@PathVariable Long courseId, @PathVariable String studentName) {

        return new ResponseEntity<>(participantsService.getAllStudentsAndGradesByCourseIdAndStudentName(courseId, studentName), HttpStatus.OK);
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
    public ResponseEntity<Long> getGradeByStudentIdAndCourseId(@PathVariable Long studentId, @PathVariable Long courseId) {

        return new ResponseEntity<>(participantsService.getGradeByStudentIdAndCourseId(studentId, courseId), HttpStatus.OK);
    }

    @GetMapping("/getAllGradesByStudentUsername/{studentUsername}")
    public ResponseEntity<List<GetGradeDTO>> getAllGradesByStudentUsername(@PathVariable String studentUsername) {

        return new ResponseEntity<>(participantsService.getAllGradesByStudentUsername(studentUsername), HttpStatus.OK);
    }

    @GetMapping("/getAllGradesByCourseNameAndStudentUsername/{courseName}/{studentUsername}")
    public ResponseEntity<List<GetGradeDTO>> getAllGradesByCourseNameAndStudentUsername(@PathVariable String courseName, @PathVariable String studentUsername) {

        return new ResponseEntity<>(participantsService.getAllGradesByCourseNameAndStudentUsername(courseName, studentUsername), HttpStatus.OK);
    }

    @GetMapping("/checkIfTheStudentIsAddedToTheCourse/{courseId}/{studentUsername}")
    public ResponseEntity<Boolean> checkIfTheStudentIsAddedToTheCourse(@PathVariable Long courseId, @PathVariable String studentUsername) {

        return new ResponseEntity<>(participantsService.checkIfTheStudentIsAddedToTheCourse(studentUsername, courseId), HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<SuccessDto> removeStudentCourseRelationship(@PathVariable Long studentId, @PathVariable Long courseId) {
        participantsService.removeStudentCourseRelationship(studentId, courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/removeGroupFromCourse/{groupNumber}/{courseId}")
    public ResponseEntity<SuccessDto> removeGroupFromCourse(@PathVariable Long groupNumber, @PathVariable Long courseId) {
        participantsService.removeGroupFromCourse(groupNumber, courseId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }
}
