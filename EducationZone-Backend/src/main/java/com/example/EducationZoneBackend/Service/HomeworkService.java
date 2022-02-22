package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.RegisterHomeworkDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Homework;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.HomeworkRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
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

    @Autowired
    public HomeworkService(HomeworkRepository homeworkRepository, ParticipantsRepository participantsRepository, StudentRepository studentRepository) {
        this.homeworkRepository = homeworkRepository;
        this.participantsRepository = participantsRepository;
        this.studentRepository = studentRepository;
    }

    public void registerHomework(RegisterHomeworkDTO registerHomeworkDTO) {
        Homework homework = Homework.builder()
                .description(registerHomeworkDTO.getDescription())
                .deadline(registerHomeworkDTO.getDeadline())
                .points(registerHomeworkDTO.getPoints())
                .course(Course.builder().id(registerHomeworkDTO.getCourseId()).build())
                .build();

        homeworkRepository.save(homework);

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

        //TODO: testez daca merge
        if (registerHomeworkDTO.getCourseId() != null)
            homework.setCourse(Course.builder().id(registerHomeworkDTO.getCourseId()).build());

        homeworkRepository.save(homework);
    }

    @SneakyThrows
    public void removeHomework(Long homeworkId) {
        Homework homework = homeworkRepository.findById(homeworkId).orElseThrow(() -> new NotFoundException("Homework not found"));
        homeworkRepository.delete(homework);
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
}
