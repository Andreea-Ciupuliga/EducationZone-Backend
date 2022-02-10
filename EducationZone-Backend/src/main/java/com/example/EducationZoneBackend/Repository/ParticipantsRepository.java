package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Models.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participants, Long> {

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO(s.firstName,s.lastName,s.email) FROM Participants p JOIN Student s ON p.student.id = s.id where p.course.id=:courseId")
    List<GetStudentDTO> findAllStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT p from Participants p where p.student.id =:studentId AND p.course.id =:courseId")
    Optional<Participants> findBystudentIdAndcourseId(@Param("studentId") Long studentId,@Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year) FROM Participants p JOIN Course c ON p.course.id = c.id where p.student.id=:studentId")
    List<GetCourseDTO> findCoursesByStudentId(@Param("studentId") Long studentId);
}
