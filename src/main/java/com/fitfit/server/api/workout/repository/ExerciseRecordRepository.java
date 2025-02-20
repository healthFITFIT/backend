package com.fitfit.server.api.workout.repository;

import com.fitfit.server.api.user.Member;
import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.dto.ExerciseResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {
    ExerciseRecord findByUserIdAndCreatedAt(Long userId, LocalDate createdAt);
    ExerciseRecord findByRecordId(Long recordId);
}