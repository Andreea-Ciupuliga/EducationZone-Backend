package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findById(Long id);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Course c")
    List<GetCourseDTO> findAllCourses();

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Course c Where c.exam.id=:examId")
    Optional<GetCourseDTO> findCourseByExamId(@Param("examId") Long examId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Course c where c.id=:courseId")
    Optional<GetCourseDTO> findCourseById(@Param("courseId") Long courseId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Course c WHERE c.professor.username=:professorUsername")
    List<GetCourseDTO> findAllCoursesByProfessorUsername(@Param("professorUsername") String professorUsername);

    Optional<Course> findByName(String name);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Course c WHERE c.name LIKE %:name%")
    List<GetCourseDTO> findAllCoursesByName(@Param("name") String name);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) FROM Course c WHERE c.professor.id=:professorId")
    List<GetCourseDTO> findAllCoursesByProfessorId(@Param("professorId") Long professorId);

    @Query("SELECT new com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester) from Course c where c.professor.username =:professorUsername AND c.id =:courseId")
    Optional<GetCourseDTO> findByCourseIdAndProfessorUsername(@Param("courseId") Long courseId, @Param("professorUsername") String professorUsername);

}
