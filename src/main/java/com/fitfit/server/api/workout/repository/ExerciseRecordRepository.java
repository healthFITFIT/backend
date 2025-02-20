package com.fitfit.server.api.workout.repository;

import com.fitfit.server.api.user.Member;
import com.fitfit.server.api.workout.domain.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRecordRepository extends JpaRepository<ExerciseRecord, Long> {
    @Query("SELECT e FROM ExerciseRecord e WHERE e.user = :user")
    List<ExerciseRecord> findByUser(@Param("user") Member user);
}