package com.example.EducationZoneBackend.Service;

import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.GetProfessorDTO;
import com.example.EducationZoneBackend.DTOs.ProfessorDTOs.RegisterProfessorDTO;
import com.example.EducationZoneBackend.Exceptions.AlreadyExistException;
import com.example.EducationZoneBackend.Exceptions.NotFoundException;
import com.example.EducationZoneBackend.Models.Professor;
import com.example.EducationZoneBackend.Repository.ProfessorRepository;
import lombok.SneakyThrows;
import org.dozer.DozerBeanMapper;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;
    private final KeycloakAdminService keycloakAdminService;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository, KeycloakAdminService keycloakAdminService) {
        this.professorRepository = professorRepository;
        this.keycloakAdminService = keycloakAdminService;
    }

    @SneakyThrows
    public void registerProfessor(RegisterProfessorDTO registerProfessorDto) {
        if (professorRepository.findByUsername(registerProfessorDto.getUsername()).isPresent()) {
            throw new AlreadyExistException("Username Already Exist");
        }

        if (professorRepository.findByEmail(registerProfessorDto.getEmail()).isPresent()) {
            throw new AlreadyExistException("Email Already Exist");
        }

        Professor professor = Professor.builder()
                .firstName(registerProfessorDto.getFirstName())
                .lastName(registerProfessorDto.getLastName())
                .email(registerProfessorDto.getEmail())
                .password(registerProfessorDto.getPassword())
                .username(registerProfessorDto.getUsername())
                .phone(registerProfessorDto.getPhone())
                .build();

        professorRepository.save(professor);
        keycloakAdminService.registerUser(registerProfessorDto.getLastName(), registerProfessorDto.getFirstName(), registerProfessorDto.getUsername(), registerProfessorDto.getPassword(), registerProfessorDto.getEmail(), "ROLE_PROFESSOR");


    }

    @SneakyThrows
    public void removeProfessor(Long professorId) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new NotFoundException("Professor not found"));

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
    public void updateProfessor(Long professorId, RegisterProfessorDTO newRegisterProfessorDto) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new NotFoundException("Professor not found"));
        UserRepresentation keycloakUser = keycloakAdminService.findUser(professor.getUsername());

        if (newRegisterProfessorDto.getFirstName() != null) {
            professor.setFirstName(newRegisterProfessorDto.getFirstName());
            keycloakUser.setFirstName(newRegisterProfessorDto.getFirstName());
        }

        if (newRegisterProfessorDto.getLastName() != null) {
            professor.setLastName(newRegisterProfessorDto.getLastName());
            keycloakUser.setLastName(newRegisterProfessorDto.getLastName());
        }
        if (newRegisterProfessorDto.getEmail() != null) {
            professor.setEmail(newRegisterProfessorDto.getEmail());
            keycloakUser.setEmail(newRegisterProfessorDto.getEmail());
        }
        if (newRegisterProfessorDto.getPassword() != null) {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(newRegisterProfessorDto.getPassword());
            credentialRepresentation.setTemporary(false);
            keycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));
        }

        if (newRegisterProfessorDto.getUsername() != null) {
            professor.setUsername(newRegisterProfessorDto.getUsername());
            keycloakUser.setUsername(newRegisterProfessorDto.getUsername());
        }
        if (newRegisterProfessorDto.getPhone() != null)
            professor.setPhone(newRegisterProfessorDto.getPhone());

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
