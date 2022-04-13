package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.GradeDTOs.GetGradeDTO;
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

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO(s.id,s.firstName,s.lastName,s.email,s.username,s.groupNumber,s.phone,s.year,s.department) FROM Participants p JOIN Student s ON p.student.id = s.id where p.course.id=:courseId")
    List<GetStudentDTO> findAllStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT p from Participants p where p.student.id =:studentId AND p.course.id =:courseId")
    Optional<Participants> findByStudentIdAndCourseId(@Param("studentId") Long studentId,@Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Participants part JOIN Course c ON part.course.id = c.id where part.student.id=:studentId")
    List<GetCourseDTO> findAllCoursesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Participants part JOIN Course c ON part.course.id = c.id where part.student.username=:studentUsername")
    List<GetCourseDTO> findAllCoursesByStudentUsername(@Param("studentUsername") String studentUsername);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.GradeDTOs.GetGradeDTO(p.course.name, p.courseGrade) FROM Participants p where p.student.id =:studentId")
    List<GetGradeDTO> findAllGradesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.GradeDTOs.GetGradeDTO(p.course.name, p.courseGrade) FROM Participants p where p.student.username =:studentUsername")
    List<GetGradeDTO> findAllGradesByStudentUsername(String studentUsername);
}
