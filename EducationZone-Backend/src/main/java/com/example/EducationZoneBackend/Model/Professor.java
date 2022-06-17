package com.example.EducationZoneBackend.Model;

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
@Table(name = "professor")
public class Professor {
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

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "professor",orphanRemoval = true,cascade = {CascadeType.ALL})
    List<Course> courses;

}
