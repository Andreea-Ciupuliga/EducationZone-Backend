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

    //gasim toate temele de la un curs
    @Query("SELECT new com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO(h.description,h.deadline,h.points,h.course.id,h.course.name) FROM Homework h JOIN Course c ON h.course.id=c.id where h.course.id=:courseId")
    List<GetHomeworkDTO> findAllHomeworksFromACourse(@Param("courseId") Long courseId);
}
