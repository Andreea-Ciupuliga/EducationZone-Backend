package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.RegisterStudentDTO;
import com.example.EducationZoneBackend.Service.StudentService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;

    }

    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerStudent(@RequestBody RegisterStudentDTO registerStudentDto) {
        studentService.registerStudent(registerStudentDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeStudent(@PathVariable Long studentId) {
        studentService.removeStudent(studentId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeAllStudents() {
        studentService.removeAllStudents();
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    @SneakyThrows
    public ResponseEntity<GetStudentDTO> getStudent(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getStudent(studentId), HttpStatus.OK);
    }

    @GetMapping("/getStudentByUsername/{studentUsername}")
    @SneakyThrows
    public ResponseEntity<GetStudentDTO> getStudentByUsername(@PathVariable String studentUsername) {
        return new ResponseEntity<>(studentService.getStudentByUsername(studentUsername), HttpStatus.OK);
    }

    @PutMapping("/{studentId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto> updateStudent(@PathVariable Long studentId, @RequestBody RegisterStudentDTO registerStudentDto) {
        studentService.updateStudent(studentId, registerStudentDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetStudentDTO>> getAllStudents() {

        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/getAllByName/{studentName}")
    public ResponseEntity<List<GetStudentDTO>> getAllStudentsByName(@PathVariable String studentName) {

        return new ResponseEntity<>(studentService.getAllStudentsByName(studentName), HttpStatus.OK);
    }
}
