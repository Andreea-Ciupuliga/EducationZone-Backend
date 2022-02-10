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
    public void addProfessorAtCourse(Long professorId, Long courseId) {

        //am nevoie de un obiect de tip professor pentru FirstName si LastName
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new NotFoundException("Professor not found"));

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        if (professorCourseRepository.findByProfessorIdAndcourseId(professorId, courseId).isPresent()) {
            throw new AlreadyExistException("Professor already at this course");
        }


        ProfessorCourse professorCourse = ProfessorCourse.builder()
                .professor(Professor.builder().id(professor.getId()).build())
                .course(Course.builder().id(course.getId()).build())
                .build();

        professorCourseRepository.save(professorCourse);

    }

    @SneakyThrows
    public List<GetProfessorDTO> findAllProfessorsByCourseId(Long courseId) {

        //Todo: verific daca courseId exista

        return professorCourseRepository.findAllProfessorsByCourseId(courseId);
    }

    @SneakyThrows
    public List<GetCourseDTO> findCoursesByProfessorId(Long professorId) {

        //Todo: verific daca professorId exista

        return professorCourseRepository.findCoursesByProfessorId(professorId);
    }

    //Todo: remove professor from course
}
