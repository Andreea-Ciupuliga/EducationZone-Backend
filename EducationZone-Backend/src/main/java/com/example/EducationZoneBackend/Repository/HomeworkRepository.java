package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO;
import com.example.EducationZoneBackend.Models.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO(h.description,h.deadline,h.points,h.course.id,h.course.name) FROM Homework h JOIN Course c ON h.course.id=c.id where h.course.id=:courseId")
    List<GetHomeworkDTO> findAllHomeworksByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO(h.description,h.deadline,h.points,h.course.id,h.course.name) FROM Homework h JOIN Course c ON h.course.id=c.id JOIN Participants p on p.course.id=c.id where p.student.id=:studentId")
    List<GetHomeworkDTO> findAllHomeworksByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO(h.description,h.deadline,h.points,h.course.id,h.course.name) FROM Homework h JOIN Course c ON h.course.id=c.id")
    List<GetHomeworkDTO> findAllHomeworks();
}
