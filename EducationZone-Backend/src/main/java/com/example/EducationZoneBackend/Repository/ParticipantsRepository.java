package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTO.GradeDTOs.GetGradeDTO;
import com.example.EducationZoneBackend.DTO.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Model.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participants, Long> {

    @Query("SELECT new com.example.EducationZoneBackend.DTO.StudentDTOs.GetStudentDTO(s.id,s.firstName,s.lastName,s.email,s.username,s.groupNumber,s.phone,s.year,s.department) FROM Participants p JOIN Student s ON p.student.id = s.id WHERE p.course.id=:courseId")
    List<GetStudentDTO> findAllStudentsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.StudentDTOs.GetStudentDTO(s.id,s.firstName,s.lastName,s.email,s.username,s.groupNumber,s.phone,s.year,s.department) FROM Participants p JOIN Student s ON p.student.id = s.id WHERE p.course.id=:courseId AND (s.lastName LIKE CONCAT(:name, '%') OR s.firstName LIKE CONCAT(:name, '%') OR CONCAT(s.lastName,' ',s.firstName) LIKE CONCAT(:name, '%') OR CONCAT(s.firstName,' ',s.lastName) LIKE CONCAT(:name, '%'))")
    List<GetStudentDTO> findAllStudentsByCourseIdAndStudentName(@Param("courseId") Long courseId, @Param("name") String name);

    @Query("SELECT p from Participants p where p.student.id =:studentId AND p.course.id =:courseId")
    Optional<Participants> findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("SELECT p from Participants p where p.student.username =:studentUsername AND p.course.id =:courseId")
    Optional<Participants> findByStudentUsernameAndCourseId(@Param("studentUsername") String studentUsername, @Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Participants part JOIN Course c ON part.course.id = c.id WHERE part.student.id=:studentId")
    List<GetCourseDTO> findAllCoursesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Participants part JOIN Course c ON part.course.id = c.id WHERE part.student.username=:studentUsername")
    List<GetCourseDTO> findAllCoursesByStudentUsername(@Param("studentUsername") String studentUsername);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.GradeDTOs.GetGradeDTO(p.course.name, p.courseGrade) FROM Participants p where p.student.id =:studentId")
    List<GetGradeDTO> findAllGradesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.GradeDTOs.GetGradeDTO(p.course.name, p.courseGrade) FROM Participants p where p.student.username =:studentUsername")
    List<GetGradeDTO> findAllGradesByStudentUsername(@Param("studentUsername") String studentUsername);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.GradeDTOs.GetGradeDTO(p.course.name, p.courseGrade) FROM Participants p JOIN Course c ON p.course.id = c.id WHERE p.student.username =:studentUsername AND c.name LIKE %:courseName%")
    List<GetGradeDTO> findAllGradesByCourseNameAndStudentUsername(@Param("courseName") String courseName, @Param("studentUsername") String studentUsername);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.GradeDTOs.GetGradeDTO(p.course.name, p.courseGrade) FROM Participants p JOIN Course c ON p.course.id = c.id WHERE p.student.id =:studentId AND c.id =:courseId")
    Optional<GetGradeDTO> findGradeByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}
