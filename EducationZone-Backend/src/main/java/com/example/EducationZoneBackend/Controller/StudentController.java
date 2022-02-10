package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.RegisterStudentDTO;
import com.example.EducationZoneBackend.Service.StudentService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //Todo: testat fara partea de keycloak si merge, trebuie sa mai verific partea cu keycloak
    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerStudent(@RequestBody RegisterStudentDTO registerStudentDto) {
        studentService.registerStudent(registerStudentDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }


    //*** MERGE ***
    //@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @DeleteMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeStudent(@RequestParam Long studentId) {
        studentService.removeStudent(studentId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }


    //*** MERGE ***
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/all")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeAllStudents() {
        studentService.removeAllStudents();
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    @SneakyThrows
    public ResponseEntity<GetStudentDTO> getStudent(@RequestParam Long studentId) {
        return new ResponseEntity<>(studentService.getStudent(studentId), HttpStatus.OK);
    }


    //*** MERGE ***
    //@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @PutMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto> putStudent(@RequestParam Long studentId, @RequestBody RegisterStudentDTO registerStudentDto) {
        studentService.putStudent(studentId, registerStudentDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    //@PreAuthorize("hasAnyRole('CLIENT_ADMIN','PROFESSOR','CLIENT_STUDENT')")
    @GetMapping("/showStudents")
    public ResponseEntity<List<GetStudentDTO>> getAllAllStudents() {

        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }


    //*** MERGE ***
    //@PreAuthorize("hasAnyRole('ADMIN','PROFESSOR','STUDENT')")
    @GetMapping("/showStudentsByName")
    public ResponseEntity<List<GetStudentDTO>> getAllStudentsByName(@RequestParam String studentName) {

        return new ResponseEntity<>(studentService.getAllStudentsByName(studentName), HttpStatus.OK);
    }


}
