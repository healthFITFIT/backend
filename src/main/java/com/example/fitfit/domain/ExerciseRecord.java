package com.example.fitfit.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



@Entity
@Table(name = "exercise_records")
@Data
public class ExerciseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseRecordId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String exerciseType;

    @Column(nullable = false)
    private LocalTime duration;

    @Column(nullable = false)
    private LocalDate startTime;

    @Column(nullable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "exerciseRecord", cascade = CascadeType.ALL)
    private List<ExerciseSet> sets;

}
