package com.example.EducationZoneBackend.Model;

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

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "examDate")
    private String examDate;

    //ce punctaj are examenul
    @Column(name = "points")
    private Long points;

    @Column(name = "examRoom")
    private String examRoom;

    @Column(name = "examHour")
    private String examHour;

    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name="course_id")
    private Course course;
}
