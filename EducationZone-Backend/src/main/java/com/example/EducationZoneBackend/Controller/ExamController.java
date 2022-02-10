package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO;
import com.example.EducationZoneBackend.DTOs.ExamDTOs.RegisterExamDTO;
import com.example.EducationZoneBackend.Service.ExamService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
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

    //*** MERGE ***
    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerExam(@RequestBody RegisterExamDTO registerExamDTO)
    {
        examService.registerExam(registerExamDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @DeleteMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto>removeExam(@RequestParam Long examId)
    {
        examService.removeExam(examId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @PutMapping()
    @SneakyThrows
    public  ResponseEntity<SuccessDto> putExam(@RequestParam Long examId, @RequestBody RegisterExamDTO registerExamDTO)
    {
        examService.putExam(examId,registerExamDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping("/showExamsForStudent")
    public ResponseEntity<List<GetExamDTO>> getAllExamsForStudent(@RequestParam Long studentId) {

        return new ResponseEntity<>(examService.getAllExamsForStudent(studentId), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping("/showExamForCourse")
    public ResponseEntity<GetExamDTO> getExamFromCourse(@RequestParam Long courseId) {

        return new ResponseEntity<>(examService.getExamFromCourse(courseId), HttpStatus.OK);
    }
}
