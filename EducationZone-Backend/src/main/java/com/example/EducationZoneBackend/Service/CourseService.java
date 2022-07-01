package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseAndProfessorNameDTO;
import com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTO.CourseDTOs.RegisterCourseDTO;
import com.example.EducationZoneBackend.DTO.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Model.Course;
import com.example.EducationZoneBackend.Model.Professor;
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

        if (registerCourseDto.getName().isEmpty() || registerCourseDto.getDescription().isEmpty() || registerCourseDto.getYear().isEmpty() || registerCourseDto.getSemester().isEmpty() || registerCourseDto.getProfessorId() == null)
            throw new NotFoundException("The record was not saved because all fields are required");

        if (courseRepository.findByName(registerCourseDto.getName()).isPresent()) {
            throw new AlreadyExistException("Course already exist");
        }

        if (professorRepository.findById(registerCourseDto.getProfessorId()).isEmpty()) {
            throw new NotFoundException("There is no professor with this id");
        }

        Course course = Course.builder()
                .name(registerCourseDto.getName())
                .numberOfStudents(0L)
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

        //lista cu toate cursurile
        List<GetCourseDTO> courses = courseRepository.findAllCourses();

        if (courses.isEmpty())
            throw new NotFoundException("There are no courses to display");

        //lista cu cursuri dar care sa contina si numele profesorului
        List<GetCourseAndProfessorNameDTO> coursesAndProfessorName = new ArrayList<>();


        //pentru fiecare curs
        for (GetCourseDTO course : courses) {
            GetProfessorDTO professor = new GetProfessorDTO();

            //vad daca exista un profesor care sa il predea. daca nu exista ii setez numele gol
            if (professorRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                //daca exista profesor ii salvez numele in obiectul de tip profesor
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
            throw new NotFoundException("Course / Courses Not Found");

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
    public Boolean checkIfTheTeacherIsTeachingTheCourse(Long courseId, String professorUsername) {

        if (courseRepository.findByCourseIdAndProfessorUsername(courseId, professorUsername).isEmpty())
            return false;
        else
            return true;
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
