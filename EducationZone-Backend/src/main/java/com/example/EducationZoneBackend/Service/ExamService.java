package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO;
import com.example.EducationZoneBackend.DTOs.ExamDTOs.RegisterExamDTO;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Exam;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.ExamRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService {

    private ExamRepository examRepository;
    private CourseRepository courseRepository;
    private ParticipantsRepository participantsRepository;
    private StudentRepository studentRepository;

    @Autowired
    public ExamService(ExamRepository examRepository, CourseRepository courseRepository, ParticipantsRepository participantsRepository, StudentRepository studentRepository) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
        this.participantsRepository = participantsRepository;
        this.studentRepository = studentRepository;
    }

    public void registerExam(RegisterExamDTO registerExamDTO) {
        Exam exam = Exam.builder()
                .description(registerExamDTO.getDescription())
                .examDate(registerExamDTO.getExamDate())
                .points(registerExamDTO.getPoints())
                .course(Course.builder().id(registerExamDTO.getCourseId()).build())
                .build();

        examRepository.save(exam);
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

        //TODO: testez daca merge
        if(registerExamDTO.getCourseId() !=null)
            exam.setCourse(Course.builder().id(registerExamDTO.getCourseId()).build());



        examRepository.save(exam);
    }

    @SneakyThrows
    public void removeExam(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new NotFoundException("Exam not found"));
        examRepository.delete(exam);
    }

    @SneakyThrows
    public GetExamDTO getExamByCourseId(Long courseId)
    {
        if(courseRepository.findById(courseId).isEmpty())
            throw new NotFoundException("Course not found");

        return examRepository.findExamByCourseId(courseId).orElseThrow(() -> new NotFoundException("Exam not found"));
    }

    @SneakyThrows
    public List<GetExamDTO> getAllExamsByStudentId(Long studentId)
    {
        if(studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        //gasim toate cursurile pentru un student si le scriem intr-o lista

        List<GetCourseDTO> courses= participantsRepository.findAllCoursesByStudentId(studentId);
        List<GetExamDTO> totalExams = new ArrayList<>();


        //parcurgem lista aia si pentru fiecare curs gasim lista de examene
        for(GetCourseDTO course : courses)
        {
            GetExamDTO exam = examRepository.findExamByCourseId(course.getId()).orElseThrow(() -> new NotFoundException("Exam not found"));

            totalExams.add(exam);
        }
        return totalExams;

    }



}
