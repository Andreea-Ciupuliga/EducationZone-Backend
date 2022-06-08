package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.RegisterProfessorDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Professor;
import com.example.EducationZoneBackend.Repository.CourseRepository;
import com.example.EducationZoneBackend.Repository.ProfessorRepository;
import com.example.EducationZoneBackend.Repository.StudentRepository;
import com.example.EducationZoneBackend.Utils.SendEmailService;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;
    private CourseRepository courseRepository;
    private final KeycloakAdminService keycloakAdminService;
    private SendEmailService sendEmailService;
    private StudentRepository studentRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository, CourseRepository courseRepository, KeycloakAdminService keycloakAdminService, SendEmailService sendEmailService, StudentRepository studentRepository) {
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.sendEmailService = sendEmailService;
        this.studentRepository = studentRepository;
    }

    @SneakyThrows
    public void registerProfessor(RegisterProfessorDTO registerProfessorDto) {
        if (professorRepository.findByUsername(registerProfessorDto.getUsername()).isPresent() || studentRepository.findByUsername(registerProfessorDto.getUsername()).isPresent()) {
            throw new AlreadyExistException("Username Already Exist");
        }

        if (professorRepository.findByEmail(registerProfessorDto.getEmail()).isPresent()) {
            throw new AlreadyExistException("Email Already Exist");
        }

        Professor professor = Professor.builder()
                .firstName(registerProfessorDto.getFirstName())
                .lastName(registerProfessorDto.getLastName())
                .email(registerProfessorDto.getEmail())
                .username(registerProfessorDto.getUsername())
                .phone(registerProfessorDto.getPhone())
                .build();

        professorRepository.save(professor);
        keycloakAdminService.registerUser(registerProfessorDto.getLastName(), registerProfessorDto.getFirstName(), registerProfessorDto.getUsername(), registerProfessorDto.getPassword(), registerProfessorDto.getEmail(), "ROLE_PROFESSOR");

        String body = "Hello " + registerProfessorDto.getFirstName() + " " + registerProfessorDto.getLastName() + "! An account was created on Education-Zone website using this email address. To log in use this email address and password " + registerProfessorDto.getPassword() + " . We recommend to change your password after logging in to your account ";
        sendEmailService.sendEmail(registerProfessorDto.getEmail(), body, "New account");

    }

    @SneakyThrows
    public void removeProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new NotFoundException("Professor not found"));
        List<UserRepresentation> keycloakUser = keycloakAdminService.findUser(professor.getUsername());

        UsersResource usersResource = KeycloakAdminService.getInstance();
        usersResource.get(keycloakUser.get(0).getId()).remove();

        professorRepository.delete(professor);
    }

    @SneakyThrows
    public GetProfessorDTO getProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new NotFoundException("Professor not found"));

        //Dest dest = mapper.map(source, Dest.class);
        GetProfessorDTO getProfessorDto = new DozerBeanMapper().map(professor, GetProfessorDTO.class);

        return getProfessorDto;
    }

    @SneakyThrows
    public Optional<GetProfessorDTO> getProfessorByCourseId(Long courseId) {

        if (courseRepository.findById(courseId).isEmpty())
            throw new NotFoundException("Course not found");

        return professorRepository.findProfessorByCourseId(courseId);
    }

    @SneakyThrows
    public GetProfessorDTO getProfessorByUsername(String professorUsername) {
        Professor professor = professorRepository.findByUsername(professorUsername).orElseThrow(() -> new NotFoundException("Professor not found"));

        //Dest dest = mapper.map(source, Dest.class);
        GetProfessorDTO getProfessorDto = new DozerBeanMapper().map(professor, GetProfessorDTO.class);

        return getProfessorDto;
    }

    @SneakyThrows
    public void updateProfessor(Long professorId, RegisterProfessorDTO newRegisterProfessorDto) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new NotFoundException("Professor not found"));
        List<UserRepresentation> keycloakUser = keycloakAdminService.findUser(professor.getUsername());

        if (newRegisterProfessorDto.getFirstName() != null) {
            professor.setFirstName(newRegisterProfessorDto.getFirstName());
            keycloakUser.get(0).setFirstName(newRegisterProfessorDto.getFirstName());
        }

        if (newRegisterProfessorDto.getLastName() != null) {
            professor.setLastName(newRegisterProfessorDto.getLastName());
            keycloakUser.get(0).setLastName(newRegisterProfessorDto.getLastName());
        }
        if (newRegisterProfessorDto.getEmail() != null) {
            professor.setEmail(newRegisterProfessorDto.getEmail());
            keycloakUser.get(0).setEmail(newRegisterProfessorDto.getEmail());
        }
        if (newRegisterProfessorDto.getPassword() != null) {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(newRegisterProfessorDto.getPassword());
            credentialRepresentation.setTemporary(false);
            keycloakUser.get(0).setCredentials(Collections.singletonList(credentialRepresentation));
        }

        if (newRegisterProfessorDto.getUsername() != null) {
            professor.setUsername(newRegisterProfessorDto.getUsername());
            keycloakUser.get(0).setUsername(newRegisterProfessorDto.getUsername());
        }
        if (newRegisterProfessorDto.getPhone() != null)
            professor.setPhone(newRegisterProfessorDto.getPhone());

        UsersResource usersResource = KeycloakAdminService.getInstance();
        usersResource.get(keycloakUser.get(0).getId()).update(keycloakUser.get(0));

        professorRepository.save(professor);

    }

    @SneakyThrows
    public List<GetProfessorDTO> getAllProfessors() {

        if (professorRepository.findAllProfessors().isEmpty())
            throw new NotFoundException("There are no professors to display");

        return professorRepository.findAllProfessors();
    }

    @SneakyThrows
    public void removeAllProfessors() {
        if (professorRepository.findAllProfessors().isEmpty())
            throw new NotFoundException("Professors Not Found");

        professorRepository.deleteAll();
    }

    @SneakyThrows
    public List<GetProfessorDTO> getAllProfessorsByName(String professorName) {

        if (professorRepository.findAllProfessorsByName(professorName).isEmpty())
            throw new NotFoundException("Professor Not Found");

        return professorRepository.findAllProfessorsByName(professorName);
    }

}
