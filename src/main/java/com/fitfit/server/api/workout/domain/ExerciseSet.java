package com.fitfit.server.api.workout.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "exercise_set")
@Data
public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setId;

    @ManyToOne
    @JoinColumn(name = "exercise_record_id", nullable = false)
    private ExerciseRecord exerciseRecord;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int setNumber;

    @Column(nullable = false)
    private int reps;

    @Column
    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseType exerciseType;

}