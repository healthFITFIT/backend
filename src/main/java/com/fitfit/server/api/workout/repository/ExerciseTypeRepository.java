package com.fitfit.server.api.workout.repository;

import com.fitfit.server.api.workout.domain.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {
}
