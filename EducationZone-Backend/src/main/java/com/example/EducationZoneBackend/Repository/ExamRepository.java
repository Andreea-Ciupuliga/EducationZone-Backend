package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO;
import com.example.EducationZoneBackend.Models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {


    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id where e.course.id=:courseId")
    Optional<GetExamDTO> findExamDtoByCourseId(@Param("courseId") Long courseId);

    Optional<Exam> findExamByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id JOIN Participants p on p.course.id=c.id where p.student.id=:studentId")
    List<GetExamDTO> findAllExamsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id JOIN Participants p on p.course.id=c.id where p.student.username=:studentUsername")
    List<GetExamDTO> findAllExamsByStudentUsername(String studentUsername);
}
