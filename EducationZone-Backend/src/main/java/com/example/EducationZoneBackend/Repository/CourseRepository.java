package com.example.EducationZoneBackend.Repository;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester,CONCAT(p.firstName, CONCAT(' ',p.lastName))) FROM Course c JOIN ProfessorCourse pc on c.id=pc.course.id JOIN Professor p on pc.professor.id = p.id")
    List<GetCourseDTO> findAllCourses();

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO(c.id,c.name,c.numberOfStudents,c.description,c.year,c.semester,CONCAT(p.firstName, CONCAT(' ',p.lastName))) FROM Course c JOIN ProfessorCourse pc on c.id=pc.course.id JOIN Professor p on pc.professor.id = p.id WHERE c.name LIKE %:name%")
    List<GetCourseDTO> findAllCoursesByName(@Param("name") String name);
}
