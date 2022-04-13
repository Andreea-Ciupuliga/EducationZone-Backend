package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO;
import com.example.EducationZoneBackend.DTOs.ExamDTOs.RegisterExamDTO;
import com.example.EducationZoneBackend.Service.ExamService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessDto> registerExam(@RequestBody RegisterExamDTO registerExamDTO) {
        examService.registerExam(registerExamDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<SuccessDto> removeExam(@PathVariable Long examId) {
        examService.removeExam(examId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @PutMapping("/{examId}")
    public ResponseEntity<SuccessDto> updateExam(@PathVariable Long examId, @RequestBody RegisterExamDTO registerExamDTO) {
        examService.updateExam(examId, registerExamDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @PutMapping("/updateExamByCourseId")
    public ResponseEntity<SuccessDto> updateExamByCourseId(@RequestBody RegisterExamDTO registerExamDTO) {
        examService.updateExamByCourseId(registerExamDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllByStudentId/{studentId}")
    public ResponseEntity<List<GetExamDTO>> getAllExamsByStudentId(@PathVariable Long studentId) {

        return new ResponseEntity<>(examService.getAllExamsByStudentId(studentId), HttpStatus.OK);
    }

    @GetMapping("/getAllExamsByStudentUsername/{studentUsername}")
    public ResponseEntity<List<GetExamDTO>> getAllExamsByStudentUsername(@PathVariable String studentUsername) {

        return new ResponseEntity<>(examService.getAllExamsByStudentUsername(studentUsername), HttpStatus.OK);
    }

    @GetMapping("/getByCourseId/{courseId}")
    public ResponseEntity<GetExamDTO> getExamByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(examService.getExamByCourseId(courseId), HttpStatus.OK);
    }
}
