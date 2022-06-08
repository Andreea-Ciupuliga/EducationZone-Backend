package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.Models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUsername(String name);

    Optional<Professor> findByEmail(String email);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO(p.id,p.firstName,p.lastName,p.email,p.username,p.phone) FROM Professor p")
    List<GetProfessorDTO> findAllProfessors();

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO(p.id,p.firstName,p.lastName,p.email,p.username,p.phone) FROM Professor p WHERE (p.lastName LIKE CONCAT(:name, '%') OR p.firstName LIKE CONCAT(:name, '%') OR CONCAT(p.lastName,' ',p.firstName) LIKE CONCAT(:name, '%') OR CONCAT(p.firstName,' ',p.lastName) LIKE CONCAT(:name, '%'))")
    List<GetProfessorDTO> findAllProfessorsByName(@Param("name") String name);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO(p.id,p.firstName,p.lastName,p.email,p.username,p.phone) FROM Course c JOIN Professor p ON c.professor.id=p.id where c.id=:courseId")
    Optional<GetProfessorDTO> findProfessorByCourseId(@Param("courseId") Long courseId);
}
