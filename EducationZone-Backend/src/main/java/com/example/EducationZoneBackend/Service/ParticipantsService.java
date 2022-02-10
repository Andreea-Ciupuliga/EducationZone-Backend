package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Participants;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantsService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private ParticipantsRepository participantsRepository;

    @Autowired
    public ParticipantsService(StudentRepository studentRepository, CourseRepository courseRepository,ParticipantsRepository participantsRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.participantsRepository = participantsRepository;
    }


    @SneakyThrows
    public void addStudentAtCourse(Long studentId, Long courseId) {

        //am nevoie de un obiect de tip student pentru FirstName si LastName
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        if (participantsRepository.findBystudentIdAndcourseId(studentId, courseId).isPresent()) {
            throw new AlreadyExistException("Student already at this course");
        }


        Participants participants = Participants.builder()
                .student(Student.builder().id(student.getId()).build())
                .course(Course.builder().id(course.getId()).build())
                .build();

        participantsRepository.save(participants);

        course.setNumberOfStudents(course.getNumberOfStudents() +1 );
        courseRepository.save(course);


    }

    @SneakyThrows
    public List<GetStudentDTO> findAllStudentsByCourseId(Long courseId) {

        //Todo: verific daca courseId exista

       return participantsRepository.findAllStudentsByCourseId(courseId);


    }

    @SneakyThrows
    public List<GetCourseDTO> findCoursesByStudentId(Long studentId) {

        //Todo: verific daca studentId exista

    return participantsRepository.findCoursesByStudentId(studentId);

    }


    //Todo: remove student from course

}
