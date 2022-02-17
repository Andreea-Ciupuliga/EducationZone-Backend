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
    public ParticipantsService(StudentRepository studentRepository, CourseRepository courseRepository, ParticipantsRepository participantsRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.participantsRepository = participantsRepository;
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

        if(courseRepository.findById(courseId).isEmpty())
            throw  new NotFoundException("Course not found");

        return participantsRepository.findAllStudentsByCourseId(courseId);


    }

    @SneakyThrows
    public List<GetCourseDTO> getAllCoursesByStudentId(Long studentId) {

        if(studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        return participantsRepository.findAllCoursesByStudentId(studentId);

    }


    @SneakyThrows
    public void removeStudentCourseRelationship(Long studentId, Long courseId) {

        if(studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        if(courseRepository.findById(courseId).isEmpty())
            throw  new NotFoundException("Course not found");

        Participants participants = participantsRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(()->new NotFoundException("Student not at this course"));

        participantsRepository.delete(participants);

    }

}
