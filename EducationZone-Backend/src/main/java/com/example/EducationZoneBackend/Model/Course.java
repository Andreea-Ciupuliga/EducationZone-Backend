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
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "numberOfStudents")
    private Long numberOfStudents;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "semester")
    private String semester;

    @Column(name = "year")
    private String year;

    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = {CascadeType.ALL})
    List<Participants> participants;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Professor professor;

    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = {CascadeType.ALL})
    List<Homework> homeworks;

    @OneToOne(mappedBy = "course", orphanRemoval = true, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Exam exam;

}
