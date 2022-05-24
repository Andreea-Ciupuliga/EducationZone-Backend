package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.Models.StickyNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickyNoteRepository extends JpaRepository<StickyNote,Long>  {
}
