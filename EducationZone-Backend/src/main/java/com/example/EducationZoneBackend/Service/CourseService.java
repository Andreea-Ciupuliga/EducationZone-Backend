package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.CourseDTOs.RegisterCourseDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @SneakyThrows
    public void registerCourse(RegisterCourseDTO registerCourseDto)
    {
        if(courseRepository.findByName(registerCourseDto.getName()).isPresent())
        {
            throw new AlreadyExistException("Course Already Exist");
        }

        Course course = Course.builder()
                .name(registerCourseDto.getName())
                .numberOfStudents(registerCourseDto.getNumberOfStudents())
                .description(registerCourseDto.getDescription())
                .year(registerCourseDto.getYear())
                .semester(registerCourseDto.getSemester())
                .build();

        courseRepository.save(course);

    }

    @SneakyThrows
    public void updateCourse(Long courseId,RegisterCourseDTO newRegisterCourseDto)
    {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new NotFoundException("Course not found"));

        if(newRegisterCourseDto.getName()!=null)
            course.setName(newRegisterCourseDto.getName());

        if(newRegisterCourseDto.getNumberOfStudents()!=null)
            course.setNumberOfStudents(newRegisterCourseDto.getNumberOfStudents());

        if(newRegisterCourseDto.getDescription()!=null)
            course.setDescription(newRegisterCourseDto.getDescription());

        if(newRegisterCourseDto.getYear()!=null)
            course.setYear(newRegisterCourseDto.getYear());

        if(newRegisterCourseDto.getSemester()!=null)
            course.setSemester(newRegisterCourseDto.getSemester());

        courseRepository.save(course);
    }

    @SneakyThrows
    public void removeCourse(Long courseId)
    {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new NotFoundException("Course not found"));
        courseRepository.delete(course);
    }

    @SneakyThrows
    public List<GetCourseDTO> getAllCourses() {

        if(courseRepository.findAllCourses().isEmpty())
            throw new NotFoundException("There are no courses to display");

        return courseRepository.findAllCourses();
    }

    @SneakyThrows
    public GetCourseDTO getCourse(Long courseId)
    {
        Course course = courseRepository.findById(courseId).orElseThrow(()->new NotFoundException("Course not found"));

        //Dest dest = mapper.map(source, Dest.class);
        GetCourseDTO getCourseDto = new DozerBeanMapper().map(course, GetCourseDTO.class);

        return getCourseDto;
    }
}
