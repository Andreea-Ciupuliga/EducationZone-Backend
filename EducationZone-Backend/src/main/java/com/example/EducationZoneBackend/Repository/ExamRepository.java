package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO;
import com.example.EducationZoneBackend.Models.Exam;
import com.example.EducationZoneBackend.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    //get exam from a course

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO(e.course.name,e.description,e.examDate,e.points,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id where e.course.id=:courseId")
    Optional<GetExamDTO> findExamFromACourse(@Param("courseId") Long courseId);
}
