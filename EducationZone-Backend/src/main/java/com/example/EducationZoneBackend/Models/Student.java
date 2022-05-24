package com.example.EducationZoneBackend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Transient
    private String password;

    @Column(name = "username")
    private String username;

    //grupa la care este studentul
    @Column(name = "groupNumber")
    private String groupNumber;

    @Column(name = "phone")
    private String phone;

    @Column(name = "year")
    private String year;

    //departamentul: cti, info, mate
    @Column(name = "department")
    private String department;

    @JsonIgnore
    @OneToMany(mappedBy = "student",orphanRemoval = true,cascade = {CascadeType.ALL})
    List<Participants> participants;

    @JsonIgnore
    @OneToMany(mappedBy = "student",orphanRemoval = true,cascade = {CascadeType.ALL})
    List<StickyNote> stickyNotes;


}
