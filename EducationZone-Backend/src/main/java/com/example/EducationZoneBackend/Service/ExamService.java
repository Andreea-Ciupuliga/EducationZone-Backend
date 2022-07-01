package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTO.ExamDTOs.GetExamDTO;
import com.example.EducationZoneBackend.DTO.ExamDTOs.RegisterExamDTO;
import com.example.EducationZoneBackend.DTO.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Model.Course;
import com.example.EducationZoneBackend.Model.Exam;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.ExamRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    private ExamRepository examRepository;
    private CourseRepository courseRepository;
    private ParticipantsRepository participantsRepository;
    private StudentRepository studentRepository;
    private SendEmailService sendEmailService;

    @Autowired
    public ExamService(ExamRepository examRepository, CourseRepository courseRepository, ParticipantsRepository participantsRepository, StudentRepository studentRepository, SendEmailService sendEmailService) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
        this.participantsRepository = participantsRepository;
        this.studentRepository = studentRepository;
        this.sendEmailService = sendEmailService;
    }

    @SneakyThrows
    public void registerExam(RegisterExamDTO registerExamDTO) {

        if (registerExamDTO.getDescription().isEmpty() || registerExamDTO.getExamDate().isEmpty() || registerExamDTO.getPoints() == null || registerExamDTO.getExamRoom().isEmpty() || registerExamDTO.getExamHour().isEmpty() || registerExamDTO.getCourseId() == null)
            throw new NotFoundException("The record was not saved because all fields are required");

        GetCourseDTO course = courseRepository.findCourseById(registerExamDTO.getCourseId()).orElseThrow(() -> new NotFoundException("Course id not found"));

        if (examRepository.findExamByCourseId(course.getId()).isPresent())
            throw new AlreadyExistException("There is already an exam for this course. Try editing the existing exam or deleting it and adding a new one.");

        Exam exam = Exam.builder()
                .description(registerExamDTO.getDescription())
                .examDate(registerExamDTO.getExamDate())
                .points(registerExamDTO.getPoints())
                .examRoom(registerExamDTO.getExamRoom())
                .examHour(registerExamDTO.getExamHour())
                .course(Course.builder().id(registerExamDTO.getCourseId()).build())
                .build();

        examRepository.save(exam);

        List<GetStudentDTO> students = participantsRepository.findAllStudentsByCourseId(registerExamDTO.getCourseId());

        students.parallelStream().forEach(student -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendEmailService.sendEmail(student.getEmail(), "Hello " + student.getFirstName() + " " + student.getLastName() + " ! A new exam for course " + course.getName() + " has been added. Go check it out! ", "New exam");
        });

    }

    @SneakyThrows
    public void updateExam(Long examId, RegisterExamDTO registerExamDTO) {

        Exam exam = examRepository.findById(examId).orElseThrow(() -> new NotFoundException("Exam not found"));

        if (registerExamDTO.getDescription() != null)
            exam.setDescription(registerExamDTO.getDescription());

        if (registerExamDTO.getExamDate() != null)
            exam.setExamDate(registerExamDTO.getExamDate());

        if (registerExamDTO.getPoints() != null)
            exam.setPoints(registerExamDTO.getPoints());

        if (registerExamDTO.getExamRoom() != null)
            exam.setExamRoom(registerExamDTO.getExamRoom());

        if (registerExamDTO.getExamHour() != null)
            exam.setExamHour(registerExamDTO.getExamHour());

//        if (registerExamDTO.getCourseId() != null) {
//            Course course = courseRepository.findById(registerExamDTO.getCourseId()).orElseThrow(() -> new NotFoundException("Course not found"));
//
//            if (examRepository.findExamByCourseId(registerExamDTO.getCourseId()).isPresent())
//                throw new AlreadyExistException("This course already has an exam");
//
//            exam.setCourse(course);
//        }

        GetCourseDTO course = courseRepository.findCourseByExamId(examId).orElseThrow(() -> new NotFoundException("Course not found"));

        examRepository.save(exam);

        List<GetStudentDTO> students = participantsRepository.findAllStudentsByCourseId(course.getId());

        students.parallelStream().forEach(student -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendEmailService.sendEmail(student.getEmail(), "Hello " + student.getFirstName() + " " + student.getLastName() + " ! The exam for course: " + exam.getCourse().getName() + " has been updated. Go check it out! ", "Updated exam");
        });
    }

    @SneakyThrows
    public void updateExamByCourseId(RegisterExamDTO registerExamDTO) {

        Exam exam = examRepository.findExamByCourseId(registerExamDTO.getCourseId()).orElseThrow(() -> new NotFoundException("Exam not found"));

        if (registerExamDTO.getDescription() != null)
            exam.setDescription(registerExamDTO.getDescription());

        if (registerExamDTO.getExamDate() != null)
            exam.setExamDate(registerExamDTO.getExamDate());

        if (registerExamDTO.getPoints() != null)
            exam.setPoints(registerExamDTO.getPoints());

        if (registerExamDTO.getExamRoom() != null)
            exam.setExamRoom(registerExamDTO.getExamRoom());

        if (registerExamDTO.getExamHour() != null)
            exam.setExamHour(registerExamDTO.getExamHour());

        if (registerExamDTO.getCourseId() != null) {
            Course course = courseRepository.findById(registerExamDTO.getCourseId()).orElseThrow(() -> new NotFoundException("Course not found"));

            if (examRepository.findExamByCourseId(registerExamDTO.getCourseId()).isPresent())
                throw new AlreadyExistException("This course already has an exam");

            exam.setCourse(course);
        }
        examRepository.save(exam);

        List<GetStudentDTO> students = participantsRepository.findAllStudentsByCourseId(registerExamDTO.getCourseId());

        students.parallelStream().forEach(student -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendEmailService.sendEmail(student.getEmail(), "Hello " + student.getFirstName() + " " + student.getLastName() + " ! The exam for course: " + exam.getCourse().getName() + " has been updated. Go check it out! ", "Updated exam");
        });

    }

    @SneakyThrows
    public void removeExam(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new NotFoundException("Exam not found"));
        GetCourseDTO course = courseRepository.findCourseById(exam.getCourse().getId()).orElseThrow(() -> new NotFoundException("Course id not found"));

        List<GetStudentDTO> students = participantsRepository.findAllStudentsByCourseId(course.getId());

        examRepository.delete(exam);

        students.parallelStream().forEach(student -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendEmailService.sendEmail(student.getEmail(), "Hello " + student.getFirstName() + " " + student.getLastName() + " ! The exam for course: " + course.getName() + " has been deleted. Go check it out! ", "Deleted exam");
        });
    }

    @SneakyThrows
    public GetExamDTO getExamByCourseId(Long courseId) {

        if (courseRepository.findById(courseId).isEmpty())
            throw new NotFoundException("Course not found");

        return examRepository.findExamDtoByCourseId(courseId).orElseThrow(() -> new NotFoundException("Exam not found"));
    }


    @SneakyThrows
    public GetExamDTO getExam(Long examId) {

        GetExamDTO getExamDTO = examRepository.findExamById(examId).orElseThrow(() -> new NotFoundException("Exam not found"));
        return getExamDTO;
    }

    @SneakyThrows
    public List<GetExamDTO> getAllExamsByStudentId(Long studentId) {
        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        if (examRepository.findAllExamsByStudentId(studentId).isEmpty())
            throw new NotFoundException("There are no exams to display");

        return examRepository.findAllExamsByStudentId(studentId);
    }

    @SneakyThrows
    public List<GetExamDTO> getAllExamsByStudentUsername(String studentUsername) {
        if (studentRepository.findByUsername(studentUsername).isEmpty())
            throw new NotFoundException("Student not found");

        return examRepository.findAllExamsByStudentUsername(studentUsername);
    }

    @SneakyThrows
    public List<GetExamDTO> getAllExamsByCourseNameAndStudentUsername(String courseName, String studentUsername) {

        if (examRepository.findAllExamsByStudentUsernameAndCourseName(studentUsername, courseName).isEmpty())
            throw new NotFoundException("There is no exam for this course or the course name is incorrect");

        return examRepository.findAllExamsByStudentUsernameAndCourseName(studentUsername, courseName);
    }

    @SneakyThrows
    public List<GetExamDTO> getAllExams() {
        if (examRepository.findAllExams().isEmpty())
            throw new NotFoundException("There are no exams to display");

        return examRepository.findAllExams();
    }
}
