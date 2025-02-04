package com.example.fitfit.domain;

import jakarta.persistence.*;

@Entity
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

    @Column(nullable = false)
    private int weight;

    @Column(nullable = false)
    private String exerciseName;

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public ExerciseRecord getExerciseRecord() {
        return exerciseRecord;
    }

    public void setExerciseRecord(ExerciseRecord exerciseRecord) {
        this.exerciseRecord = exerciseRecord;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
}
