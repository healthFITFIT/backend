package com.fitfit.server.api.workout.repository;

import com.fitfit.server.api.workout.domain.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
}