package com.example.EducationZoneBackend.Repository;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByUsername(String name);

    Optional<Student> findByEmail(String email);

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO(s.firstName,s.lastName,s.email) FROM Student s")
    List<GetStudentDTO> findAllStudents();

    @Query("SELECT new com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO(s.firstName,s.lastName,s.email) FROM Student s WHERE s.lastName LIKE %:name% OR s.firstName LIKE %:name%")
    List<GetStudentDTO> findAllStudentsByName(@Param("name")String name);
}
