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
@Table(name = "participants")
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "courseGrade")
    private String courseGrade;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Student student;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Course course;



}
