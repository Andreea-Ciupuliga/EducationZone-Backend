package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.GetHomeworkDTO;
import com.example.EducationZoneBackend.DTOs.HomeworkDTOs.RegisterHomeworkDTO;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Homework;
import com.example.EducationZoneBackend.Repository.HomeworkRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomeworkService {

    private HomeworkRepository homeworkRepository;
    private ParticipantsRepository participantsRepository;

    @Autowired
    public HomeworkService(HomeworkRepository homeworkRepository, ParticipantsRepository participantsRepository) {
        this.homeworkRepository = homeworkRepository;
        this.participantsRepository = participantsRepository;
    }

    public void registerHomework(RegisterHomeworkDTO registerHomeworkDTO)
    {
        Homework homework = Homework.builder()
                .description(registerHomeworkDTO.getDescription())
                .deadline(registerHomeworkDTO.getDeadline())
                .points(registerHomeworkDTO.getPoints())
                .course(Course.builder().id(registerHomeworkDTO.getCourseId()).build())
                .build();

        homeworkRepository.save(homework);

    }

    @SneakyThrows
    public void putHomework(Long homeworkId,RegisterHomeworkDTO registerHomeworkDTO)
    {
        Homework homework = homeworkRepository.findById(homeworkId).orElseThrow(()->new NotFoundException("Homework not found"));

        if(registerHomeworkDTO.getDescription() !=null)
            homework.setDescription(registerHomeworkDTO.getDescription());

        if(registerHomeworkDTO.getDeadline() !=null)
            homework.setDeadline(registerHomeworkDTO.getDeadline());

        if(registerHomeworkDTO.getPoints() !=null)
            homework.setPoints(registerHomeworkDTO.getPoints());


        homeworkRepository.save(homework);
    }

    @SneakyThrows
    public void removeHomework(Long homeworkId)
    {
        Homework homework = homeworkRepository.findById(homeworkId).orElseThrow(()->new NotFoundException("Homework not found"));
        homeworkRepository.delete(homework);
    }

    public List<GetHomeworkDTO> getAllHomeworksForAStudent(Long studentId)
    {
        //TODO: verific si daca id ul de la student exista

        //gasim toate cursurile pentru un student si le scriem intr-o lista

        List<GetCourseDTO> courses= participantsRepository.findCoursesByStudentId(studentId);

        List<GetHomeworkDTO> totalHomeworks = new ArrayList<>();

        //parcurgem lista aia si pentru fiecare curs gasim lista de teme
        for(GetCourseDTO course : courses)
        {
            List<GetHomeworkDTO> homeworks = homeworkRepository.findAllHomeworksFromACourse(course.getId());

            for(GetHomeworkDTO homework : homeworks)
                totalHomeworks.add(homework);
        }
        return totalHomeworks;

    }
}
