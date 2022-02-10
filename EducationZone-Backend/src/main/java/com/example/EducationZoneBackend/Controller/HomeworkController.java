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

    //*** MERGE ***
    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerHomework(@RequestBody RegisterHomeworkDTO registerHomeworkDTO)
    {
        homeworkService.registerHomework(registerHomeworkDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @DeleteMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto>removeHomework(@RequestParam Long homeworkId)
    {
        homeworkService.removeHomework(homeworkId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping("/showHomeworksForAStudent")
    public ResponseEntity<List<GetHomeworkDTO>> getAllHomeworksForAStudent(@RequestParam Long studentId) {

        return new ResponseEntity<>(homeworkService.getAllHomeworksForAStudent(studentId), HttpStatus.OK);
    }


    //*** MERGE ***
    @PutMapping()
    @SneakyThrows
    public  ResponseEntity<SuccessDto> putHomework(@RequestParam Long homeworkId, @RequestBody RegisterHomeworkDTO registerHomeworkDTO)
    {
        homeworkService.putHomework(homeworkId,registerHomeworkDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }
}
