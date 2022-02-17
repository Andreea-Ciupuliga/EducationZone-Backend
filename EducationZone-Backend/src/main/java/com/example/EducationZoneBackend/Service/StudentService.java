package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.DTOs.StudentDTOs.RegisterStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Student;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
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
    public void registerStudent(RegisterStudentDTO registerStudentDto) {
        if (studentRepository.findByUsername(registerStudentDto.getUsername()).isPresent()) {
            throw new AlreadyExistException("Username Already Exist");
        }

        if (studentRepository.findByEmail(registerStudentDto.getEmail()).isPresent()) {
            throw new AlreadyExistException("Email Already Exist");
        }

        Student student = Student.builder()
                .firstName(registerStudentDto.getFirstName())
                .lastName(registerStudentDto.getLastName())
                .email(registerStudentDto.getEmail())
                .password(registerStudentDto.getPassword())
                .username(registerStudentDto.getUsername())
                .groupNumber(registerStudentDto.getGroupNumber())
                .phone(registerStudentDto.getPhone())
                .year(registerStudentDto.getYear())
                .department(registerStudentDto.getDepartment())
                .build();

        studentRepository.save(student);
        //keycloakAdminService.registerUser(registerStudentDto.getUsername(), registerStudentDto.getPassword(), "ROLE_STUDENT");

    }


    @SneakyThrows
    public void removeStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("student not found"));
        studentRepository.delete(student);

    }

    @SneakyThrows
    public GetStudentDTO getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("student not found"));

        //Dest dest = mapper.map(source, Dest.class);
        GetStudentDTO getStudentDto = new DozerBeanMapper().map(student, GetStudentDTO.class);

        return getStudentDto;
    }

    @SneakyThrows
    public void updateStudent(Long studentId, RegisterStudentDTO newRegisterStudentDto) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("student not found"));

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

        if (newRegisterStudentDto.getGroupNumber() != null)
            student.setGroupNumber(newRegisterStudentDto.getGroupNumber());

        if (newRegisterStudentDto.getPhone() != null)
            student.setPhone(newRegisterStudentDto.getPhone());

        if (newRegisterStudentDto.getYear() != null)
            student.setYear(newRegisterStudentDto.getYear());

        if (newRegisterStudentDto.getDepartment() != null)
            student.setDepartment(newRegisterStudentDto.getDepartment());

        studentRepository.save(student);

    }


    @SneakyThrows
    public List<GetStudentDTO> getAllStudents() {

        if (studentRepository.findAllStudents().isEmpty())
            throw new NotFoundException("there are no students to display");

        return studentRepository.findAllStudents();
    }

    @SneakyThrows
    public List<GetStudentDTO> getAllStudentsByName(String studentName) {

        if (studentRepository.findAllStudentsByName(studentName).isEmpty())
            throw new NotFoundException("Students Not Found");


        return studentRepository.findAllStudentsByName(studentName);
    }

    @SneakyThrows
    public void removeAllStudents() {

        if (studentRepository.findAllStudents().isEmpty())
            throw new NotFoundException("Students Not Found");

        studentRepository.deleteAll();
    }
}
