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
    @JoinColumn(name = "recordId")
    private ExerciseRecord exerciseRecord;

    @ManyToOne
    @JoinColumn(name = "exerciseType")
    private ExerciseType exerciseType;

    private int reps;
    private float weight;

}