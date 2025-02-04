package com.example.fitfit.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



@Entity
public class ExerciseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseRecordId;

    @Column(nullable = false)
    private Long userId;

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

    public Long getExerciseRecordId() {
        return exerciseRecordId;
    }

    public void setExerciseRecordId(Long exerciseRecordId) {
        this.exerciseRecordId = exerciseRecordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }
}
