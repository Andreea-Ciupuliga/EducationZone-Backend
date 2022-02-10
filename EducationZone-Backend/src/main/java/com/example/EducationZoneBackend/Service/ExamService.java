package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.ExamDTOs.GetExamDTO;
import com.example.EducationZoneBackend.DTOs.ExamDTOs.RegisterExamDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Exam;
import com.example.EducationZoneBackend.Repository.ExamRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService {

    private ExamRepository examRepository;
    private ParticipantsRepository participantsRepository;

    @Autowired
    public ExamService(ExamRepository examRepository, ParticipantsRepository participantsRepository) {
        this.examRepository = examRepository;
        this.participantsRepository = participantsRepository;
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
    public void putExam(Long examId, RegisterExamDTO registerExamDTO) {

        Exam exam = examRepository.findById(examId).orElseThrow(() -> new NotFoundException("Exam not found"));

        if (registerExamDTO.getDescription() != null)
            exam.setDescription(registerExamDTO.getDescription());

        if (registerExamDTO.getExamDate() != null)
            exam.setExamDate(registerExamDTO.getExamDate());

        if (registerExamDTO.getPoints() != null)
            exam.setPoints(registerExamDTO.getPoints());


        examRepository.save(exam);
    }

    @SneakyThrows
    public void removeExam(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new NotFoundException("Exam not found"));
        examRepository.delete(exam);
    }

    @SneakyThrows
    public GetExamDTO getExamFromCourse(Long courseId)
    {
        //TODO: verific si daca id ul de la curs exista

        return examRepository.findExamFromACourse(courseId).orElseThrow(() -> new NotFoundException("Exam not found"));
    }

    @SneakyThrows
    public List<GetExamDTO> getAllExamsForStudent(Long studentId)
    {
        //TODO: verific si daca id ul de la student exista

        //gasim toate cursurile pentru un student si le scriem intr-o lista

        List<GetCourseDTO> courses= participantsRepository.findCoursesByStudentId(studentId);
        List<GetExamDTO> totalExams = new ArrayList<>();


        //parcurgem lista aia si pentru fiecare curs gasim lista de examene
        for(GetCourseDTO course : courses)
        {
            GetExamDTO exam = examRepository.findExamFromACourse(course.getId()).orElseThrow(() -> new NotFoundException("Exam not found"));

            totalExams.add(exam);
        }
        return totalExams;

    }



}
