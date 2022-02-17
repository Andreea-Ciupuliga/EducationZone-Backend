package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.RegisterStudentDTO;
import com.example.EducationZoneBackend.Service.StudentService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
import org.hibernate.annotations.Parameter;
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

    //@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @DeleteMapping("/{studentId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeStudent(@PathVariable Long studentId) {
        studentService.removeStudent(studentId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteAll")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeAllStudents() {
        studentService.removeAllStudents();
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{studentId}")
    @SneakyThrows
    public ResponseEntity<GetStudentDTO> getStudent(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getStudent(studentId), HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @PutMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto> updateStudent(@RequestParam Long studentId, @RequestBody RegisterStudentDTO registerStudentDto) {
        studentService.updateStudent(studentId, registerStudentDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('CLIENT_ADMIN','PROFESSOR','CLIENT_STUDENT')")
    @GetMapping("/getAll")
    public ResponseEntity<List<GetStudentDTO>> getAllStudents() {

        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','PROFESSOR','STUDENT')")
    @GetMapping("/getAllByName")
    public ResponseEntity<List<GetStudentDTO>> getAllStudentsByName(@RequestParam String studentName) {

        return new ResponseEntity<>(studentService.getAllStudentsByName(studentName), HttpStatus.OK);
    }
}
