package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTO.ExamDTOs.GetExamDTO;
import com.example.EducationZoneBackend.Model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {


    @Query("SELECT new com.example.EducationZoneBackend.DTO.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.examRoom,e.examHour,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id where e.course.id=:courseId")
    Optional<GetExamDTO> findExamDtoByCourseId(@Param("courseId") Long courseId);

    Optional<Exam> findExamByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.examRoom,e.examHour,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id JOIN Participants p on p.course.id=c.id where p.student.id=:studentId")
    List<GetExamDTO> findAllExamsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.examRoom,e.examHour,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id JOIN Participants p on p.course.id=c.id where p.student.username=:studentUsername")
    List<GetExamDTO> findAllExamsByStudentUsername(String studentUsername);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.examRoom,e.examHour,e.course.id) FROM Exam e ")
    List<GetExamDTO> findAllExams();

    @Query("SELECT new com.example.EducationZoneBackend.DTO.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.examRoom,e.examHour,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id where e.course.name LIKE %:courseName%")
    List<GetExamDTO> findAllExamsByCourseName(@Param("courseName") String courseName);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.ExamDTOs.GetExamDTO(e.id,e.course.name,e.description,e.examDate,e.points,e.examRoom,e.examHour,e.course.id) FROM Exam e JOIN Course c ON e.course.id=c.id JOIN Participants p on p.course.id=c.id where p.student.username=:studentUsername and e.course.name LIKE %:courseName%")
    List<GetExamDTO> findAllExamsByStudentUsernameAndCourseName(@Param("studentUsername") String studentUsername, @Param("courseName") String courseName);
}
