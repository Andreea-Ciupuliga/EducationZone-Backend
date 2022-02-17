package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Professor;
import com.example.EducationZoneBackend.Models.ProfessorCourse;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.ProfessorCourseRepository;
import com.example.EducationZoneBackend.Repository.ProfessorRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorCourseService {

    private ProfessorCourseRepository professorCourseRepository;
    private ProfessorRepository professorRepository;
    private CourseRepository courseRepository;

    @Autowired
    public ProfessorCourseService(ProfessorCourseRepository professorCourseRepository, ProfessorRepository professorRepository, CourseRepository courseRepository) {
        this.professorCourseRepository = professorCourseRepository;
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
    }

    @SneakyThrows
    public void registerProfessorToCourse(Long professorId, Long courseId) {

        //am nevoie de un obiect de tip professor pentru FirstName si LastName

        if(professorRepository.findById(professorId).isEmpty())
            throw  new NotFoundException("Professor not found");

        if(courseRepository.findById(courseId).isEmpty())
            throw  new NotFoundException("Course not found");

        if (professorCourseRepository.findByProfessorIdAndCourseId(professorId, courseId).isPresent()) {
            throw new AlreadyExistException("Professor already at this course");
        }

        ProfessorCourse professorCourse = ProfessorCourse.builder()
                .professor(Professor.builder().id(professorId).build())
                .course(Course.builder().id(courseId).build())
                .build();

        professorCourseRepository.save(professorCourse);

    }

    @SneakyThrows
    public List<GetProfessorDTO> getAllProfessorsByCourseId(Long courseId) {

        if(courseRepository.findById(courseId).isEmpty())
            throw  new NotFoundException("Course not found");

        return professorCourseRepository.findAllProfessorsByCourseId(courseId);
    }

    @SneakyThrows
    public List<GetCourseDTO> getAllCoursesByProfessorId(Long professorId) {

        if(professorRepository.findById(professorId).isEmpty())
        throw  new NotFoundException("Professor not found");

        return professorCourseRepository.findAllCoursesByProfessorId(professorId);
    }

    @SneakyThrows
    public void removeCourseProfessorRelationship(Long professorId, Long courseId) {

        if(professorRepository.findById(professorId).isEmpty())
            throw  new NotFoundException("Professor not found");

        if(courseRepository.findById(courseId).isEmpty())
            throw  new NotFoundException("Course not found");

        ProfessorCourse professorCourse= professorCourseRepository.findByProfessorIdAndCourseId(professorId, courseId).orElseThrow(()->new NotFoundException("Professor not at this course"));

        professorCourseRepository.delete(professorCourse);

    }

}
