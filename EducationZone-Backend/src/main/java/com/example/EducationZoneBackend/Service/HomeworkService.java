package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.RegisterHomeworkDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Homework;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.HomeworkRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import com.example.EducationZoneBackend.Utils.SendEmailService;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomeworkService {

    private HomeworkRepository homeworkRepository;
    private ParticipantsRepository participantsRepository;
    private StudentRepository studentRepository;
    private SendEmailService sendEmailService;
    private CourseRepository courseRepository;

    @Autowired
    public HomeworkService(HomeworkRepository homeworkRepository, ParticipantsRepository participantsRepository, StudentRepository studentRepository, SendEmailService sendEmailService, CourseRepository courseRepository) {
        this.homeworkRepository = homeworkRepository;
        this.participantsRepository = participantsRepository;
        this.studentRepository = studentRepository;
        this.sendEmailService = sendEmailService;
        this.courseRepository = courseRepository;
    }

    @SneakyThrows
    public void registerHomework(RegisterHomeworkDTO registerHomeworkDTO) {

        GetCourseDTO course = courseRepository.findCourseById(registerHomeworkDTO.getCourseId()).orElseThrow(() -> new NotFoundException("Course id not found"));

        Homework homework = Homework.builder()
                .description(registerHomeworkDTO.getDescription())
                .deadline(registerHomeworkDTO.getDeadline())
                .points(registerHomeworkDTO.getPoints())
                .course(Course.builder().id(registerHomeworkDTO.getCourseId()).build())
                .build();

        homeworkRepository.save(homework);

        List<GetStudentDTO> students = participantsRepository.findAllStudentsByCourseId(registerHomeworkDTO.getCourseId());

        for (GetStudentDTO student : students) {
            String body = "Hello " + student.getFirstName() + " " + student.getLastName() + " ! A new homework for course: " + course.getName() + " has been added. Go check it out! ";
            sendEmailService.sendEmail(student.getEmail(), body, "New homework");
        }

    }

    @SneakyThrows
    public void updateHomework(Long homeworkId, RegisterHomeworkDTO registerHomeworkDTO) {

        Homework homework = homeworkRepository.findById(homeworkId).orElseThrow(() -> new NotFoundException("Homework not found"));

        if (registerHomeworkDTO.getDescription() != null)
            homework.setDescription(registerHomeworkDTO.getDescription());

        if (registerHomeworkDTO.getDeadline() != null)
            homework.setDeadline(registerHomeworkDTO.getDeadline());

        if (registerHomeworkDTO.getPoints() != null)
            homework.setPoints(registerHomeworkDTO.getPoints());

        if (registerHomeworkDTO.getCourseId() != null) {
            Course course = courseRepository.findById(registerHomeworkDTO.getCourseId()).orElseThrow(() -> new NotFoundException("Course not found"));
            homework.setCourse(course);
        }
        homeworkRepository.save(homework);

        List<GetStudentDTO> students = participantsRepository.findAllStudentsByCourseId(registerHomeworkDTO.getCourseId());

        for (GetStudentDTO student : students) {
            String body = "Hello " + student.getFirstName() + " " + student.getLastName() + " ! A homework for course: " + homework.getCourse().getName() + " has been updated. Go check it out! ";
            sendEmailService.sendEmail(student.getEmail(), body, "Updated homework");
        }
    }

    @SneakyThrows
    public void removeHomework(Long homeworkId) {
        Homework homework = homeworkRepository.findById(homeworkId).orElseThrow(() -> new NotFoundException("Homework not found"));
        GetCourseDTO course = courseRepository.findCourseById(homework.getCourse().getId()).orElseThrow(() -> new NotFoundException("Course id not found"));

        List<GetStudentDTO> students = participantsRepository.findAllStudentsByCourseId(course.getId());

        homeworkRepository.delete(homework);

        for (GetStudentDTO student : students) {
            String body = "Hello " + student.getFirstName() + " " + student.getLastName() + " ! A homework for course: " + course.getName() + " has been deleted. Go check it out! ";
            sendEmailService.sendEmail(student.getEmail(), body, "Deleted homework");
        }

    }

    @SneakyThrows
    public List<GetHomeworkDTO> getAllHomeworksByStudentId(Long studentId) {
        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        return homeworkRepository.findAllHomeworksByStudentId(studentId);
    }

    @SneakyThrows
    public List<GetHomeworkDTO> getAllHomeworksByCourseId(Long courseId) {
        return homeworkRepository.findAllHomeworksByCourseId(courseId);

    }

    @SneakyThrows
    public GetHomeworkDTO getHomework(Long homeworkId) {
        Homework homework = homeworkRepository.findById(homeworkId).orElseThrow(() -> new NotFoundException("Homework not found"));

        //Dest dest = mapper.map(source, Dest.class);
        GetHomeworkDTO getHomeworkDTO = new DozerBeanMapper().map(homework, GetHomeworkDTO.class);

        return getHomeworkDTO;
    }

    @SneakyThrows
    public List<GetHomeworkDTO> getAllHomeworks() {

        if (homeworkRepository.findAllHomeworks().isEmpty())
            throw new NotFoundException("There are no homeworks to display");

        return homeworkRepository.findAllHomeworks();
    }

    @SneakyThrows
    public List<GetHomeworkDTO> getAllHomeworksByStudentUsername(String username) {
        if (studentRepository.findByUsername(username).isEmpty())
            throw new NotFoundException("Student not found");

        return homeworkRepository.findAllHomeworksByStudentUsername(username);
    }

    @SneakyThrows
    public List<GetHomeworkDTO> getAllHomeworksByCourseNameAndStudentUsername(String courseName, String username) {
        if (studentRepository.findByUsername(username).isEmpty())
            throw new NotFoundException("Student not found");

        return homeworkRepository.findAllHomeworksByCourseNameAndStudentUsername(courseName, username);
    }
}
