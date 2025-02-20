package com.fitfit.server.api.workout.repository;

import com.fitfit.server.api.user.Member;
import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.dto.ExerciseResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {
    ExerciseRecord findByUserIdAndCreatedAt(Long userId, LocalDate createdAt);
    ExerciseRecord findByRecordId(Long recordId);

    @Query("SELECT e FROM ExerciseRecord e WHERE e.user = :user")
    List<ExerciseRecord> findByUser(@Param("user") Member user);
}