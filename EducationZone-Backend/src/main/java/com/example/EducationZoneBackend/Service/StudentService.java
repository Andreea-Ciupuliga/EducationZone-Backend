package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.RegisterStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private final KeycloakAdminService keycloakAdminService; //ca sa pot face salvarea de useri si in keycloak


    @Autowired
    public StudentService(StudentRepository studentRepository, KeycloakAdminService keycloakAdminService) {
        this.studentRepository = studentRepository;

        this.keycloakAdminService = keycloakAdminService;
    }



    @SneakyThrows
    public void registerStudent(RegisterStudentDTO registerStudentDto)
    {
        System.out.println(registerStudentDto);

        if(studentRepository.findByUsername(registerStudentDto.getUsername()).isPresent())
        {
            throw new AlreadyExistException("Username Already Exist");
        }

        if(studentRepository.findByEmail(registerStudentDto.getEmail()).isPresent())
        {
            throw new AlreadyExistException("Email Already Exist");
        }

        Student student= Student.builder()
                .firstName(registerStudentDto.getFirstName())
                .lastName(registerStudentDto.getLastName())
                .email(registerStudentDto.getEmail())
                .password(registerStudentDto.getPassword())
                .username(registerStudentDto.getUsername())
                .studentSet(registerStudentDto.getStudentSet())
                .year(registerStudentDto.getYear())
                .studentGroup(registerStudentDto.getStudentGroup())
                .phone(registerStudentDto.getPhone())
                .build();

        studentRepository.save(student);
       // keycloakAdminService.registerUser(registerStudentDto.getUsername(), registerStudentDto.getPassword(), "ROLE_STUDENT");


    }


    @SneakyThrows
    public void removeStudent(Long studentId)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(()->new NotFoundException("student not found"));

        studentRepository.delete(student);

    }

    @SneakyThrows
    public GetStudentDTO getStudent(Long studentId)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(()->new NotFoundException("student not found"));

        GetStudentDTO getStudentDto= GetStudentDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail()).build();

        return getStudentDto;
    }

    @SneakyThrows
    public void putStudent(Long studentId, RegisterStudentDTO newRegisterStudentDto)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(()->new NotFoundException("student not found"));

        //daca este admin sau este chiar studentul


            if (newRegisterStudentDto.getFirstName() != null)
                student.setFirstName(newRegisterStudentDto.getFirstName());

            if (newRegisterStudentDto.getLastName() != null)
                student.setLastName(newRegisterStudentDto.getLastName());

            if (newRegisterStudentDto.getEmail() != null)
                student.setEmail(newRegisterStudentDto.getEmail());

            if (newRegisterStudentDto.getPassword() != null)
                student.setPassword(newRegisterStudentDto.getPassword());

            if (newRegisterStudentDto.getUsername() != null)
                student.setUsername(newRegisterStudentDto.getUsername());


            studentRepository.save(student);


    }


    @SneakyThrows
    public List<GetStudentDTO> getAllStudents() {

        if(studentRepository.findAllStudents().isEmpty())
            throw new NotFoundException("there are no students to display");

        return studentRepository.findAllStudents();
    }

    @SneakyThrows
    public List<GetStudentDTO> getAllStudentsByName(String studentName) {


        if(studentRepository.findAllStudentsByName(studentName).isEmpty())
        {
            throw new NotFoundException("Students Not Found");
        }

        return studentRepository.findAllStudentsByName(studentName);
    }

    public void removeAllStudents() {
        studentRepository.deleteAll();
    }
}
