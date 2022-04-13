package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.RegisterHomeworkDTO;
import com.example.EducationZoneBackend.Service.HomeworkService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/homework")
public class HomeworkController {

    private HomeworkService homeworkService;

    @Autowired
    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessDto> registerHomework(@RequestBody RegisterHomeworkDTO registerHomeworkDTO) {
        homeworkService.registerHomework(registerHomeworkDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{homeworkId}")
    public ResponseEntity<SuccessDto> removeHomework(@PathVariable Long homeworkId) {
        homeworkService.removeHomework(homeworkId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllByStudentId/{studentId}")
    public ResponseEntity<List<GetHomeworkDTO>> getAllHomeworksByStudentId(@PathVariable Long studentId) {

        return new ResponseEntity<>(homeworkService.getAllHomeworksByStudentId(studentId), HttpStatus.OK);
    }

    @GetMapping("/getAllByStudentUsername/{username}")
    public ResponseEntity<List<GetHomeworkDTO>> getAllHomeworksByStudentUsername(@PathVariable String username) {

        return new ResponseEntity<>(homeworkService.getAllHomeworksByStudentUsername(username), HttpStatus.OK);
    }

    @GetMapping("/getAllByCourseId/{courseId}")
    public ResponseEntity<List<GetHomeworkDTO>> getAllHomeworksByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(homeworkService.getAllHomeworksByCourseId(courseId), HttpStatus.OK);
    }

    @PutMapping("/{homeworkId}")
    public ResponseEntity<SuccessDto> updateHomework(@PathVariable Long homeworkId, @RequestBody RegisterHomeworkDTO registerHomeworkDTO) {
        homeworkService.updateHomework(homeworkId, registerHomeworkDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/{homeworkId}")
    public ResponseEntity<GetHomeworkDTO> getHomework(@PathVariable Long homeworkId) {
        return new ResponseEntity<>(homeworkService.getHomework(homeworkId), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetHomeworkDTO>> getAllHomeworks() {
        return new ResponseEntity<>(homeworkService.getAllHomeworks(), HttpStatus.OK);
    }
}
