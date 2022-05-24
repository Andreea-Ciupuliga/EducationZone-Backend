package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseAndProfessorNameDTO;
import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.CourseDTOs.RegisterCourseDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Professor;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.ProfessorRepository;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private ProfessorRepository professorRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @SneakyThrows
    public void registerCourse(RegisterCourseDTO registerCourseDto) {
        if (courseRepository.findByName(registerCourseDto.getName()).isPresent()) {
            throw new AlreadyExistException("Course Already Exist");
        }

        Course course = Course.builder()
                .name(registerCourseDto.getName())
                .numberOfStudents(registerCourseDto.getNumberOfStudents())
                .description(registerCourseDto.getDescription())
                .year(registerCourseDto.getYear())
                .semester(registerCourseDto.getSemester())
                .professor(Professor.builder().id(registerCourseDto.getProfessorId()).build())
                .build();

        courseRepository.save(course);

    }

    @SneakyThrows
    public void updateCourse(Long courseId, RegisterCourseDTO newRegisterCourseDto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        if (newRegisterCourseDto.getName() != null)
            course.setName(newRegisterCourseDto.getName());

        if (newRegisterCourseDto.getNumberOfStudents() != null)
            course.setNumberOfStudents(newRegisterCourseDto.getNumberOfStudents());

        if (newRegisterCourseDto.getDescription() != null)
            course.setDescription(newRegisterCourseDto.getDescription());

        if (newRegisterCourseDto.getYear() != null)
            course.setYear(newRegisterCourseDto.getYear());

        if (newRegisterCourseDto.getSemester() != null)
            course.setSemester(newRegisterCourseDto.getSemester());

        if (newRegisterCourseDto.getProfessorId() != null) {
            Professor professor = professorRepository.findById(newRegisterCourseDto.getProfessorId()).orElseThrow(() -> new NotFoundException("Professor not found"));
            course.setProfessor(professor);
        }
        courseRepository.save(course);
    }

    @SneakyThrows
    public void removeCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));
        courseRepository.delete(course);
    }

    @SneakyThrows
    public List<GetCourseAndProfessorNameDTO> getAllCourses() {

        List<GetCourseDTO> courses = courseRepository.findAllCourses();

        if (courses.isEmpty())
            throw new NotFoundException("There are no courses to display");

        List<GetCourseAndProfessorNameDTO> coursesAndProfessorName = new ArrayList<>();


        for (GetCourseDTO course : courses) {
            GetProfessorDTO professor = new GetProfessorDTO();

            if (professorRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                professor.setFirstName(professorRepository.findProfessorByCourseId(course.getId()).get().getFirstName());
                professor.setLastName(professorRepository.findProfessorByCourseId(course.getId()).get().getLastName());
            }

            GetCourseAndProfessorNameDTO getCourseAndProfessorNameDTO = new DozerBeanMapper().map(course, GetCourseAndProfessorNameDTO.class);
            getCourseAndProfessorNameDTO.setProfessorName(professor.getFirstName() + " " + professor.getLastName());
            coursesAndProfessorName.add(getCourseAndProfessorNameDTO);
        }

        return coursesAndProfessorName;
    }

    @SneakyThrows
    public GetCourseAndProfessorNameDTO getCourse(Long courseId) {
        GetCourseDTO getCourseDto = courseRepository.findCourseById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        GetProfessorDTO professor = new GetProfessorDTO();

        if (professorRepository.findProfessorByCourseId(getCourseDto.getId()).isEmpty()) {
            professor.setFirstName("");
            professor.setLastName("");
        } else {
            professor.setFirstName(professorRepository.findProfessorByCourseId(getCourseDto.getId()).get().getFirstName());
            professor.setLastName(professorRepository.findProfessorByCourseId(getCourseDto.getId()).get().getLastName());
        }

        GetCourseAndProfessorNameDTO getCoursAndProfessorNameDTO = new DozerBeanMapper().map(getCourseDto, GetCourseAndProfessorNameDTO.class);
        getCoursAndProfessorNameDTO.setProfessorName(professor.getFirstName() + " " + professor.getLastName());

        return getCoursAndProfessorNameDTO;
    }

    @SneakyThrows
    public List<GetCourseAndProfessorNameDTO> getAllCoursesByName(String courseName) {

        List<GetCourseDTO> courses = courseRepository.findAllCoursesByName(courseName);

        if (courses.isEmpty())
            throw new NotFoundException("Coursess Not Found");

        List<GetCourseAndProfessorNameDTO> coursesAndProfessorName = new ArrayList<>();

        for (GetCourseDTO course : courses) {
            GetProfessorDTO professor = new GetProfessorDTO();

            if (professorRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                professor.setFirstName(professorRepository.findProfessorByCourseId(course.getId()).get().getFirstName());
                professor.setLastName(professorRepository.findProfessorByCourseId(course.getId()).get().getLastName());
            }

            GetCourseAndProfessorNameDTO getCourseAndProfessorNameDTO = new DozerBeanMapper().map(course, GetCourseAndProfessorNameDTO.class);
            getCourseAndProfessorNameDTO.setProfessorName(professor.getFirstName() + " " + professor.getLastName());
            coursesAndProfessorName.add(getCourseAndProfessorNameDTO);
        }

        return coursesAndProfessorName;
    }

    @SneakyThrows
    public List<GetCourseAndProfessorNameDTO> getAllCoursesByProfessorUsername(String professorUsername) {
        if (professorRepository.findByUsername(professorUsername).isEmpty())
            throw new NotFoundException("Professor not found");

        List<GetCourseDTO> courses = courseRepository.findAllCoursesByProfessorUsername(professorUsername);
        List<GetCourseAndProfessorNameDTO> coursesAndProfessorName = new ArrayList<>();

        for (GetCourseDTO course : courses) {

            GetProfessorDTO professor = new GetProfessorDTO();

            if (professorRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                professor.setFirstName(professorRepository.findProfessorByCourseId(course.getId()).get().getFirstName());
                professor.setLastName(professorRepository.findProfessorByCourseId(course.getId()).get().getLastName());
            }

            GetCourseAndProfessorNameDTO getCourseAndProfessorNameDTO = new DozerBeanMapper().map(course, GetCourseAndProfessorNameDTO.class);
            getCourseAndProfessorNameDTO.setProfessorName(professor.getFirstName() + " " + professor.getLastName());
            coursesAndProfessorName.add(getCourseAndProfessorNameDTO);

        }

        return coursesAndProfessorName;

    }

    @SneakyThrows
    public List<GetCourseAndProfessorNameDTO> getAllCoursesByProfessorId(Long professorId) {

        if (professorRepository.findById(professorId).isEmpty())
            throw new NotFoundException("Professor not found");

        List<GetCourseDTO> courses = courseRepository.findAllCoursesByProfessorId(professorId);
        List<GetCourseAndProfessorNameDTO> coursesAndProfessorName = new ArrayList<>();

        for (GetCourseDTO course : courses) {

            GetProfessorDTO professor = new GetProfessorDTO();

            if (professorRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                professor.setFirstName(professorRepository.findProfessorByCourseId(course.getId()).get().getFirstName());
                professor.setLastName(professorRepository.findProfessorByCourseId(course.getId()).get().getLastName());
            }

            GetCourseAndProfessorNameDTO getCourseAndProfessorNameDTO = new DozerBeanMapper().map(course, GetCourseAndProfessorNameDTO.class);
            getCourseAndProfessorNameDTO.setProfessorName(professor.getFirstName() + " " + professor.getLastName());
            coursesAndProfessorName.add(getCourseAndProfessorNameDTO);

        }

        return coursesAndProfessorName;

    }
}
