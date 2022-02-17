package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.Models.ProfessorCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorCourseRepository  extends JpaRepository<ProfessorCourse, Long> {

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO(pr.firstName,pr.lastName,pr.email,pr.username,pr.phone) FROM ProfessorCourse pc JOIN Professor pr ON pc.professor.id = pr.id where pc.course.id=:courseId")
    List<GetProfessorDTO> findAllProfessorsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM ProfessorCourse p JOIN Course c ON p.course.id = c.id where p.professor.id=:professorId")
    List<GetCourseDTO> findAllCoursesByProfessorId(@Param("professorId") Long professorId);

    @Query("SELECT p from ProfessorCourse p where p.professor.id =:professorId AND p.course.id =:courseId")
    Optional<ProfessorCourse> findByProfessorIdAndCourseId(@Param("professorId") Long professorId,@Param("courseId") Long courseId);
}
