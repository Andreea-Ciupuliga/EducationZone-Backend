package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTO.CourseDTOs.GetCourseDTO;
import com.example.EducationZoneBackend.DTO.StudentDTOs.GetStudentDTO;
import com.example.EducationZoneBackend.DTO.StudentDTOs.RegisterStudentDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Model.Student;
import com.example.EducationZoneBackend.Repository.ParticipantsRepository;
import com.example.EducationZoneBackend.Repository.ProfessorRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {

    private StudentRepository studentRepository;
    private final KeycloakAdminService keycloakAdminService; //ca sa pot face salvarea de useri si in keycloak
    private SendEmailService sendEmailService;
    private ProfessorRepository professorRepository;
    private ParticipantsRepository participantsRepository;
    private ParticipantsService participantsService;

    @Autowired
    public StudentService(StudentRepository studentRepository, KeycloakAdminService keycloakAdminService, SendEmailService sendEmailService, ProfessorRepository professorRepository, ParticipantsRepository participantsRepository, ParticipantsService participantsService) {
        this.studentRepository = studentRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.sendEmailService = sendEmailService;
        this.professorRepository = professorRepository;
        this.participantsRepository = participantsRepository;
        this.participantsService = participantsService;
    }

    @SneakyThrows
    public void registerStudent(RegisterStudentDTO registerStudentDto) {

        if (registerStudentDto.getFirstName().isEmpty() || registerStudentDto.getLastName().isEmpty() || registerStudentDto.getEmail().isEmpty() || registerStudentDto.getPassword().isEmpty() || registerStudentDto.getUsername().isEmpty() || registerStudentDto.getGroupNumber() == null || registerStudentDto.getPhone().isEmpty() || registerStudentDto.getYear().isEmpty() || registerStudentDto.getDepartment().isEmpty())
            throw new NotFoundException("The record was not saved because all fields are required");

        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(registerStudentDto.getEmail());

        if (matcher.matches() == false)
            throw new NotFoundException("Invalid email format");

        if (studentRepository.findByUsername(registerStudentDto.getUsername()).isPresent() || professorRepository.findByUsername(registerStudentDto.getUsername()).isPresent()) {
            throw new AlreadyExistException("Username Already Exist");
        }

        if (studentRepository.findByEmail(registerStudentDto.getEmail()).isPresent()) {
            throw new AlreadyExistException("Email Already Exist");
        }
        Student student = Student.builder()
                .firstName(registerStudentDto.getFirstName())
                .lastName(registerStudentDto.getLastName())
                .email(registerStudentDto.getEmail())
                .username(registerStudentDto.getUsername())
                .groupNumber(registerStudentDto.getGroupNumber())
                .phone(registerStudentDto.getPhone())
                .year(registerStudentDto.getYear())
                .department(registerStudentDto.getDepartment())
                .build();

        studentRepository.save(student);
        keycloakAdminService.registerUser(registerStudentDto.getLastName(), registerStudentDto.getFirstName(), registerStudentDto.getUsername(), registerStudentDto.getPassword(),
                registerStudentDto.getEmail(), "ROLE_STUDENT");

        String body = "Hello " + registerStudentDto.getFirstName() + " " + registerStudentDto.getLastName() + "! An account was created on Education-Zone web platform using this email address." +
                " To log in use this email address and password " + registerStudentDto.getPassword() + " . We recommend to change your password after logging in to your account ";
        sendEmailService.sendEmail(registerStudentDto.getEmail(), body, "New account");
    }


    @SneakyThrows
    public void removeStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        List<UserRepresentation> keycloakUser = keycloakAdminService.findUser(student.getUsername());

        UsersResource usersResource = KeycloakAdminService.getInstance();
        usersResource.get(keycloakUser.get(0).getId()).remove();

        //gasesc toate cursurile la care este inscris studentul si scad numarul de studenti de la fiecare curs
        List<GetCourseDTO> courses = participantsRepository.findAllCoursesByStudentId(studentId);

        if (!courses.isEmpty())
            for (GetCourseDTO course : courses) {
                participantsService.removeStudentCourseRelationship(studentId, course.getId());
            }

        studentRepository.delete(student);
    }

    @SneakyThrows
    public GetStudentDTO getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));

        //Dest dest = mapper.map(source, Dest.class);
        GetStudentDTO getStudentDto = new DozerBeanMapper().map(student, GetStudentDTO.class);

        return getStudentDto;
    }

    @SneakyThrows
    public GetStudentDTO getStudentByUsername(String studentUsername) {
        Student student = studentRepository.findByUsername(studentUsername).orElseThrow(() -> new NotFoundException("Student not found"));

        //Dest dest = mapper.map(source, Dest.class);
        GetStudentDTO getStudentDto = new DozerBeanMapper().map(student, GetStudentDTO.class);

        return getStudentDto;
    }

    @SneakyThrows
    public void updateStudent(Long studentId, RegisterStudentDTO newRegisterStudentDto) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Student not found"));
        List<UserRepresentation> keycloakUser = keycloakAdminService.findUser(student.getUsername());

        if (newRegisterStudentDto.getFirstName() != null) {
            student.setFirstName(newRegisterStudentDto.getFirstName());
            keycloakUser.get(0).setFirstName(newRegisterStudentDto.getFirstName());
        }

        if (newRegisterStudentDto.getLastName() != null) {
            student.setLastName(newRegisterStudentDto.getLastName());
            keycloakUser.get(0).setLastName(newRegisterStudentDto.getLastName());
        }

        if (newRegisterStudentDto.getEmail() != null) {
            student.setEmail(newRegisterStudentDto.getEmail());
            keycloakUser.get(0).setEmail(newRegisterStudentDto.getEmail());
        }
        if (newRegisterStudentDto.getPassword() != null) {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(newRegisterStudentDto.getPassword());
            credentialRepresentation.setTemporary(false);
            keycloakUser.get(0).setCredentials(Collections.singletonList(credentialRepresentation));
        }

        if (newRegisterStudentDto.getUsername() != null) {
            student.setUsername(newRegisterStudentDto.getUsername());
            keycloakUser.get(0).setUsername(newRegisterStudentDto.getUsername());
        }
        if (newRegisterStudentDto.getGroupNumber() != null)
            student.setGroupNumber(newRegisterStudentDto.getGroupNumber());

        if (newRegisterStudentDto.getPhone() != null)
            student.setPhone(newRegisterStudentDto.getPhone());

        if (newRegisterStudentDto.getYear() != null)
            student.setYear(newRegisterStudentDto.getYear());

        if (newRegisterStudentDto.getDepartment() != null)
            student.setDepartment(newRegisterStudentDto.getDepartment());

        UsersResource usersResource = KeycloakAdminService.getInstance();
        usersResource.get(keycloakUser.get(0).getId()).update(keycloakUser.get(0));
        studentRepository.save(student);

    }


    @SneakyThrows
    public List<GetStudentDTO> getAllStudents() {

        if (studentRepository.findAllStudents().isEmpty())
            throw new NotFoundException("There are no students to display");

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
