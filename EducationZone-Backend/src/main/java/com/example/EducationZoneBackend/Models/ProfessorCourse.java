package com.example.EducationZoneBackend.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "professorCourse")
public class ProfessorCourse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Professor professor;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Course course;

}
