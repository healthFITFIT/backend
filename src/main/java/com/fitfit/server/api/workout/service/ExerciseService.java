package com.fitfit.server.api.workout.service;

import com.fitfit.server.api.user.Member;
import com.fitfit.server.api.user.repository.MemberRepository;
import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.domain.ExerciseSet;
import com.fitfit.server.api.workout.domain.ExerciseType;
import com.fitfit.server.api.workout.dto.ExerciseRecordRequest;
import com.fitfit.server.api.workout.dto.ExerciseRequest;
import com.fitfit.server.api.workout.dto.SetRequest;
import com.fitfit.server.api.workout.repository.ExerciseRecordRepository;
import com.fitfit.server.api.workout.repository.ExerciseSetRepository;
import com.fitfit.server.api.workout.repository.ExerciseTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRecordRepository exerciseRecordRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveRecord(ExerciseRecordRequest request) {
        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExerciseRecord exerciseRecord = new ExerciseRecord(member, request.getDuration(), request.getDate());
        exerciseRecordRepository.save(exerciseRecord);

        for (ExerciseRequest exerciseRequest : request.getExercises()) {
            ExerciseType exerciseType = exerciseTypeRepository.findByName(exerciseRequest.getName())
                    .orElseThrow(() -> new RuntimeException("ExerciseType not found"));

            for (SetRequest setRequest : exerciseRequest.getSets()) {
                ExerciseSet exerciseSet = new ExerciseSet();
                exerciseSet.setExerciseRecord(exerciseRecord);
                exerciseSet.setExerciseType(exerciseType);
                exerciseSet.setReps(setRequest.getReps());
                exerciseSet.setWeight(setRequest.getWeight());

                exerciseSetRepository.save(exerciseSet);
            }
        }
    }

    @Transactional
    public void deleteRecord(Long recordId, Long userId) {
        ExerciseRecord record = exerciseRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("ExerciseRecord not found"));

        if (!record.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this record");
        }

        exerciseRecordRepository.delete(record);
    }

    public void deleteSet(Long setId, Long recordId, Long userId) {
        ExerciseSet exerciseSet = exerciseSetRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise set not found"));

        if (!exerciseSet.getExerciseRecord().getRecordId().equals(recordId)) {
            throw new IllegalArgumentException("Set does not belong to the given record");
        }

        if (!exerciseSet.getExerciseRecord().getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this set");
        }

        exerciseSetRepository.delete(exerciseSet);
    }

    public void updateSet(Long setId, Long recordId, Long userId, SetRequest request) {
        ExerciseSet exerciseSet = exerciseSetRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise set not found"));

        if (!exerciseSet.getExerciseRecord().getRecordId().equals(recordId)) {
            throw new IllegalArgumentException("Set does not belong to the given record");
        }

        if (!exerciseSet.getExerciseRecord().getUser().getUserId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update this set");
        }

        exerciseSet.setReps(request.getReps());
        exerciseSet.setWeight(request.getWeight());

        exerciseSetRepository.save(exerciseSet);
    }
}