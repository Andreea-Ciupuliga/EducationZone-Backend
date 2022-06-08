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
@Table(name = "homework")
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", length = 15000)
    private String description;

    @Column(name = "deadline")
    private String deadline;

    //ce punctaj are tema respectiva
    @Column(name = "points")
    private String points;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Course course;

}
