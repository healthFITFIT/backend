package com.fitfit.server.api.workout.repository;

import com.fitfit.server.api.workout.domain.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
    List<ExerciseSet> findExerciseSetsByRecordId(Long recordId);
    List<ExerciseSet> findExerciseSetsByExerciseType(String exerciseType);
}