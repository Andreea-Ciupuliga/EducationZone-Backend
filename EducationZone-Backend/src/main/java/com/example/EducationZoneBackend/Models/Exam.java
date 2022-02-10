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
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "examDate")
    private String examDate;

    //ce punctaj are examenul
    @Column(name = "points")
    private String points;

    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="course_id")
    private Course course;
}
