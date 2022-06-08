package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseAndProfessorNameDTO;
import com.example.EducationZoneBackend.DTOs.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTOs.GradeDTOs.GetGradeDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentAndGradeDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Course;
import com.example.EducationZoneBackend.Models.Participants;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import com.example.EducationZoneBackend.Repository.ProfessorRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import com.example.EducationZoneBackend.Utils.SendEmailService;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantsService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private ParticipantsRepository participantsRepository;
    private ProfessorRepository professorRepository;
    private SendEmailService sendEmailService;

    @Autowired
    public ParticipantsService(StudentRepository studentRepository, CourseRepository courseRepository, ParticipantsRepository participantsRepository, ProfessorRepository professorRepository, SendEmailService sendEmailService) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.participantsRepository = participantsRepository;
        this.professorRepository = professorRepository;
        this.sendEmailService = sendEmailService;
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
    public void registerGroupAtCourse(Long groupNumber, Long courseId) {

        int newStudents = 0;
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        //lista cu studentii de la grupa respectiva
        List<GetStudentDTO> students = studentRepository.findAllStudentsByGroupNumber(groupNumber);

        if (students.isEmpty())
            throw new NotFoundException("There are no students in this group");

        //pentru fiecare student
        for (GetStudentDTO student : students) {

            //il adaugam la curs doar daca nu exista deja
            if (participantsRepository.findByStudentIdAndCourseId(student.getId(), courseId).isEmpty()) {

                Participants participants = Participants.builder()
                        .student(Student.builder().id(student.getId()).build())
                        .course(Course.builder().id(courseId).build())
                        .build();

                participantsRepository.save(participants);
                newStudents++;
            }
        }

        course.setNumberOfStudents(course.getNumberOfStudents() + newStudents);
        courseRepository.save(course);
    }

    @SneakyThrows
    public void removeGroupFromCourse(Long groupNumber, Long courseId) {

        int deletedStudents = 0;
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        //lista cu studentii de la grupa respectiva
        List<GetStudentDTO> students = studentRepository.findAllStudentsByGroupNumber(groupNumber);

        if (students.isEmpty())
            throw new NotFoundException("There are no students in this group");

        //pentru fiecare student
        for (GetStudentDTO student : students) {

            //il stergem de la curs doar daca este scris la curs
            if (participantsRepository.findByStudentIdAndCourseId(student.getId(), courseId).isPresent()) {

                Participants participants = participantsRepository.findByStudentIdAndCourseId(student.getId(), courseId).orElseThrow(() -> new NotFoundException("Student not at this course"));

                participantsRepository.delete(participants);
                deletedStudents++;
            }
        }

        course.setNumberOfStudents(course.getNumberOfStudents() - deletedStudents);
        courseRepository.save(course);
    }

    @SneakyThrows
    public void addGradeForStudent(Long studentId, Long courseId, Long courseGrade) {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        GetCourseDTO course = courseRepository.findCourseById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));
        Participants participants = participantsRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new NotFoundException("Student not at this course"));

        participants.setCourseGrade(courseGrade);

        participantsRepository.save(participants);

        String body = "Hello " + student.getFirstName() + " " + student.getLastName() + " ! You have a new grade: " + courseGrade + " at course: " + course.getName() + ". Go check it out! ";
        sendEmailService.sendEmail(student.getEmail(), body, "New grade");

    }

    @SneakyThrows
    public List<GetStudentDTO> getAllStudentsByCourseId(Long courseId) {

        if (courseRepository.findById(courseId).isEmpty())
            throw new NotFoundException("Course not found");

        return participantsRepository.findAllStudentsByCourseId(courseId);


    }

    @SneakyThrows
    public List<GetStudentAndGradeDTO> getAllStudentsAndGradesByCourseId(Long courseId) {

        if (courseRepository.findById(courseId).isEmpty())
            throw new NotFoundException("Course not found");

        //iau toti studentii de la cursul respectiv
        List<GetStudentDTO> students = participantsRepository.findAllStudentsByCourseId(courseId);
        List<GetStudentAndGradeDTO> studentsAndGrades = new ArrayList<>();

        for (GetStudentDTO student : students) {//pentru fiecare student caut nota


            GetGradeDTO grade = new GetGradeDTO();

            //daca nu are nota la cursul respectiv o setez ""
            if (participantsRepository.findGradeByStudentIdAndCourseId(student.getId(), courseId).isEmpty()) {
                grade.setCourseGrade(Long.valueOf(""));
            } else {
                //daca are nota o salvez
                grade.setCourseGrade(participantsRepository.findGradeByStudentIdAndCourseId(student.getId(), courseId).get().getCourseGrade());
            }

            GetStudentAndGradeDTO getStudentAndGradeDTO = new DozerBeanMapper().map(student, GetStudentAndGradeDTO.class);
            getStudentAndGradeDTO.setGrade(grade.getCourseGrade());
            studentsAndGrades.add(getStudentAndGradeDTO);

        }

        return studentsAndGrades;
    }

    @SneakyThrows
    public List<GetCourseAndProfessorNameDTO> getAllCoursesByStudentId(Long studentId) {

        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        List<GetCourseDTO> courses = participantsRepository.findAllCoursesByStudentId(studentId);
        List<GetCourseAndProfessorNameDTO> coursesAndProfessorName = new ArrayList<>();


        for (GetCourseDTO course : courses) {

            GetProfessorDTO professor = new GetProfessorDTO();

            if (professorRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                professor.setFirstName(professorRepository.findProfessorByCourseId(course.getId()).get().getFirstName());
                professor.setLastName(professorRepository.findProfessorByCourseId(course.getId()).get().getLastName());
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

            if (professorRepository.findProfessorByCourseId(course.getId()).isEmpty()) {
                professor.setFirstName("");
                professor.setLastName("");
            } else {
                professor.setFirstName(professorRepository.findProfessorByCourseId(course.getId()).get().getFirstName());
                professor.setLastName(professorRepository.findProfessorByCourseId(course.getId()).get().getLastName());
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
    public Long getGradeByStudentIdAndCourseId(Long studentId, Long courseId) {

        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        if (courseRepository.findById(courseId).isEmpty())
            throw new NotFoundException("Course not found");

        Participants participants = participantsRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new NotFoundException("Student not at this course"));
        return participants.getCourseGrade();

    }


    @SneakyThrows
    public void removeStudentCourseRelationship(Long studentId, Long courseId) {

        if (studentRepository.findById(studentId).isEmpty())
            throw new NotFoundException("Student not found");

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));

        Participants participants = participantsRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new NotFoundException("Student not at this course"));

        participantsRepository.delete(participants);

        course.setNumberOfStudents(course.getNumberOfStudents() - 1);
        courseRepository.save(course);
    }

    @SneakyThrows
    public List<GetGradeDTO> getAllGradesByStudentUsername(String studentUsername) {
        if (studentRepository.findByUsername(studentUsername).isEmpty())
            throw new NotFoundException("Student not found");

        return participantsRepository.findAllGradesByStudentUsername(studentUsername);
    }

    @SneakyThrows
    public List<GetGradeDTO> getAllGradesByCourseNameAndStudentUsername(String courseName, String studentUsername) {
        if (studentRepository.findByUsername(studentUsername).isEmpty())
            throw new NotFoundException("Student not found");

        return participantsRepository.findAllGradesByCourseNameAndStudentUsername(courseName, studentUsername);
    }


}
