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
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "numberOfStudents")
    private Long numberOfStudents;

    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private Long year;

    @JsonIgnore
    @OneToMany(mappedBy = "course",orphanRemoval = true,cascade = {CascadeType.ALL})
    List<Participants> participants;

    @JsonIgnore
    @OneToMany(mappedBy = "course",orphanRemoval = true,cascade = {CascadeType.ALL})
    List<ProfessorCourse> professorCourse;

    @JsonIgnore
    @OneToMany(mappedBy = "course",orphanRemoval = true,cascade = {CascadeType.ALL})
    List<Homework> homeworks;

    @JsonIgnore
    @OneToOne(mappedBy = "course",orphanRemoval = true,cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private Exam exam;

}
