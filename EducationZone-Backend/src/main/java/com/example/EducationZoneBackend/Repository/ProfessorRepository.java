package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long> {
    Optional<Professor> findByUsername(String name);

    Optional<Professor> findByEmail(String email);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO(p.firstName,p.lastName,p.email,p.username,p.phone) FROM Professor p")
    List<GetProfessorDTO> findAllProfessors();
}
