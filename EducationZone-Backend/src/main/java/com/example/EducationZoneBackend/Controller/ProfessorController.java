package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTO.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.DTO.ProfessorDTOs.RegisterProfessorDTO;
import com.example.EducationZoneBackend.Service.ProfessorService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping("/register")
    @SneakyThrows
    public ResponseEntity<SuccessDto> registerProfessor(@RequestBody RegisterProfessorDTO registerProfessorDto) {
        professorService.registerProfessor(registerProfessorDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllByName/{professorName}")
    public ResponseEntity<List<GetProfessorDTO>> getAllProfessorsByName(@PathVariable String professorName) {
        return new ResponseEntity<>(professorService.getAllProfessorsByName(professorName), HttpStatus.OK);
    }

    @PutMapping("/update/{professorId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto> updateProfessor(@PathVariable Long professorId,
                                                      @RequestBody RegisterProfessorDTO registerProfessorDto) {
        professorService.updateProfessor(professorId, registerProfessorDto);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{professorId}")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeProfessor(@PathVariable Long professorId) {
        professorService.removeProfessor(professorId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    @SneakyThrows
    public ResponseEntity<SuccessDto> removeAllProfessors() {
        professorService.removeAllProfessors();
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/{professorId}")
    @SneakyThrows
    public ResponseEntity<GetProfessorDTO> getProfessor(@PathVariable Long professorId) {
        return new ResponseEntity<>(professorService.getProfessor(professorId), HttpStatus.OK);
    }

    @GetMapping("/getProfessorByCourseId/{courseId}")
    public ResponseEntity<Optional<GetProfessorDTO>> getProfessorByCourseId(@PathVariable Long courseId) {

        return new ResponseEntity<>(professorService.getProfessorByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/getProfessorByUsername/{professorUsername}")
    @SneakyThrows
    public ResponseEntity<GetProfessorDTO> getProfessorByUsername(@PathVariable String professorUsername) {
        return new ResponseEntity<>(professorService.getProfessorByUsername(professorUsername), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetProfessorDTO>> getAllProfessors() {

        return new ResponseEntity<>(professorService.getAllProfessors(), HttpStatus.OK);
    }
}
