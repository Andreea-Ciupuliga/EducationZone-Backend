package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTO.StickyNoteDTOs.GetStickyNoteDTO;
import com.example.EducationZoneBackend.Model.StickyNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StickyNoteRepository extends JpaRepository<StickyNote, Long> {

    @Query("SELECT new com.example.EducationZoneBackend.DTO.StickyNoteDTOs.GetStickyNoteDTO(sn.id,sn.title,sn.description) FROM StickyNote sn JOIN Student s ON sn.student.id=s.id where sn.student.username=:username")
    List<GetStickyNoteDTO> findAllStickyNotesByStudentUsername(@Param("username") String username);
}
