package com.example.EducationZoneBackend.Controller;

import com.example.EducationZoneBackend.DTOs.StickyNoteDTOs.GetStickyNoteDTO;
import com.example.EducationZoneBackend.DTOs.StickyNoteDTOs.RegisterStickyNoteDTO;
import com.example.EducationZoneBackend.Service.StickyNoteService;
import com.example.EducationZoneBackend.Utils.SuccessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stickyNote")
public class StickyNoteController {

    private StickyNoteService stickyNoteService;

    @Autowired
    public StickyNoteController(StickyNoteService stickyNoteService) {
        this.stickyNoteService = stickyNoteService;
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessDto> registerStickyNote(@RequestBody RegisterStickyNoteDTO registerStickyNoteDTO) {
        stickyNoteService.createStickyNote(registerStickyNoteDTO);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @DeleteMapping("/{stickyNoteId}")
    public ResponseEntity<SuccessDto> removeStickyNote(@PathVariable Long stickyNoteId) {
        stickyNoteService.removeStickyNote(stickyNoteId);
        return new ResponseEntity<>(new SuccessDto(), HttpStatus.OK);
    }

    @GetMapping("/getAllByStudentUsername/{username}")
    public ResponseEntity<List<GetStickyNoteDTO>> getAllStickyNotesByStudentUsername(@PathVariable String username) {

        return new ResponseEntity<>(stickyNoteService.getAllStickyNotesByStudentUsername(username), HttpStatus.OK);
    }
}
