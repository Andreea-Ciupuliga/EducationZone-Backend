package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.RegisterProfessorDTO;
import com.example.EducationZoneBackend.Service.ProfessorService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    //*** MERGE ***
    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerProfessor(@RequestBody RegisterProfessorDTO registerProfessorDto) {
        professorService.registerProfessor(registerProfessorDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeProfessor(@RequestParam Long professorId) {
        professorService.removeProfessor(professorId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeAllProfessors() {
        professorService.removeAllProfessors();
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping()
    @SneakyThrows
    public ResponseEntity<GetProfessorDTO> getProfessor(@RequestParam Long professorId) {
        return new ResponseEntity<>(professorService.getProfessor(professorId), HttpStatus.OK);
    }

    @PutMapping()
    @SneakyThrows
    public ResponseEntity<SuccessDto> putProfessor(@RequestParam Long professorId, @RequestBody RegisterProfessorDTO registerProfessorDto) {
        professorService.putProfessor(professorId, registerProfessorDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    //*** MERGE ***
    @GetMapping("/showProfessors")
    public ResponseEntity<List<GetProfessorDTO>> getAllAllProfessors() {

        return new ResponseEntity<>(professorService.getAllProfessors(), HttpStatus.OK);
    }


}
