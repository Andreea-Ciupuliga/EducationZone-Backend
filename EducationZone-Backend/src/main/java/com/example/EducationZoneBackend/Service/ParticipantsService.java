package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseAndProfessorNameDTO;
import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.GradeDTOs.GetGradeDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Participants;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import com.example.EducationZoneBackend.Repository.ProfessorCourseRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipantsService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private ParticipantsRepository participantsRepository;
    private ProfessorCourseRepository professorCourseRepository;

    @Autowired
    public ParticipantsService(StudentRepository studentRepository, CourseRepository courseRepository, ParticipantsRepository participantsRepository, ProfessorCourseRepository professorCourseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.participantsRepository = participantsRepository;
        this.professorCourseRepository = professorCourseRepository;
    }


    @SneakyThrows
    public void registerStudentAtCourse(Long studentId, Long courseId) {

        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        if (participantsRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent()) {
            throw new AlreadyExistException("Student already at this course");
        }


        Participants participants = Participants.builder()
                .student(Student.builder().id(studentId).build())
                .course(Course.builder().id(courseId).build())
                .build();

        participantsRepository.save(participants);

        course.setNumberOfStudents(course.getNumberOfStudents() + 1);
        courseRepository.save(course);


    }

    @SneakyThrows
    public List<GetStudentDTO> getAllStudentsByCourseId(Long courseId) {

        if (courseRepository.findById(courseId).isEmpty())
            throw new NotFoundException("Course not found");

        return participantsRepository.findAllStudentsByCourseId(courseId);


    }

    @SneakyThrows
    public List<GetCourseAndProfessorNameDTO> getAllCoursesByStudentId(Long studentId) {

        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        List<GetCourseDTO> courses = participantsRepository.findAllCoursesByStudentId(studentId);
        List<GetCourseAndProfessorNameDTO> coursesAndProfessorName = new ArrayList<>();


        for (GetCourseDTO course : courses) {

            GetProfessorDTO professor = new GetProfessorDTO();

            if (professorCourseRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                professor.setFirstName(professorCourseRepository.findProfessorByCourseId(course.getId()).get().getFirstName());
                professor.setLastName(professorCourseRepository.findProfessorByCourseId(course.getId()).get().getLastName());
            }

            GetCourseAndProfessorNameDTO getCourseAndProfessorNameDTO = new DozerBeanMapper().map(course, GetCourseAndProfessorNameDTO.class);
            getCourseAndProfessorNameDTO.setProfessorName(professor.getFirstName() + " " + professor.getLastName());
            coursesAndProfessorName.add(getCourseAndProfessorNameDTO);

        }


        return coursesAndProfessorName;
    }

    @SneakyThrows
    public List<GetCourseAndProfessorNameDTO> getAllCoursesByStudentUsername(String studentUsername) {

        if (studentRepository.findByUsername(studentUsername).isEmpty())
            throw new NotFoundException("Student not found");

        List<GetCourseDTO> courses = participantsRepository.findAllCoursesByStudentUsername(studentUsername);
        List<GetCourseAndProfessorNameDTO> coursesAndProfessorName = new ArrayList<>();


        for (GetCourseDTO course : courses) {

            GetProfessorDTO professor = new GetProfessorDTO();

            if (professorCourseRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                professor.setFirstName(professorCourseRepository.findProfessorByCourseId(course.getId()).get().getFirstName());
                professor.setLastName(professorCourseRepository.findProfessorByCourseId(course.getId()).get().getLastName());
            }

            GetCourseAndProfessorNameDTO getCourseAndProfessorNameDTO = new DozerBeanMapper().map(course, GetCourseAndProfessorNameDTO.class);
            getCourseAndProfessorNameDTO.setProfessorName(professor.getFirstName() + " " + professor.getLastName());
            coursesAndProfessorName.add(getCourseAndProfessorNameDTO);

        }


        return coursesAndProfessorName;

    }


    @SneakyThrows
    public List<GetGradeDTO> getAllGradesByStudentId(Long studentId) {

        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        return participantsRepository.findAllGradesByStudentId(studentId);

    }


    @SneakyThrows
    public void removeStudentCourseRelationship(Long studentId, Long courseId) {

        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        if (courseRepository.findById(courseId).isEmpty())
            throw new NotFoundException("Course not found");

        Participants participants = participantsRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new NotFoundException("Student not at this course"));

        participantsRepository.delete(participants);

    }

    @SneakyThrows
    public List<GetGradeDTO> getAllGradesByStudentUsername(String studentUsername) {
        if (studentRepository.findByUsername(studentUsername).isEmpty())
            throw new NotFoundException("Student not found");

        return participantsRepository.findAllGradesByStudentUsername(studentUsername);
    }
}
