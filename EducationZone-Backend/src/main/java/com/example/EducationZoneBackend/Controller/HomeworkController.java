package com.example.EducationZoneBackend.Controller;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.RegisterHomeworkDTO;
import com.example.EducationZoneBackend.Service.HomeworkService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
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
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerHomework(@RequestBody RegisterHomeworkDTO registerHomeworkDTO)
    {
        homeworkService.registerHomework(registerHomeworkDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{homeworkId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto>removeHomework(@PathVariable Long homeworkId)
    {
        homeworkService.removeHomework(homeworkId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllByStudentId")
    public ResponseEntity<List<GetHomeworkDTO>> getAllHomeworksByStudentId(@RequestParam Long studentId) {

        return new ResponseEntity<>(homeworkService.getAllHomeworksByStudentId(studentId), HttpStatus.OK);
    }

    @PutMapping()
    @SneakyThrows
    public  ResponseEntity<SuccessDto> updateHomework(@RequestParam Long homeworkId, @RequestBody RegisterHomeworkDTO registerHomeworkDTO)
    {
        homeworkService.updateHomework(homeworkId,registerHomeworkDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/{homeworkId}")
    @SneakyThrows
    public ResponseEntity<GetHomeworkDTO> getHomework(@PathVariable Long homeworkId) {
        return new ResponseEntity<>(homeworkService.getHomework(homeworkId), HttpStatus.OK);
    }
}
