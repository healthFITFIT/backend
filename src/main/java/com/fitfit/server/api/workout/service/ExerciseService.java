package com.fitfit.server.api.workout.service;

import com.fitfit.server.api.user.Member;
import com.fitfit.server.api.user.repository.MemberRepository;
import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.domain.ExerciseSet;
import com.fitfit.server.api.workout.domain.ExerciseType;
import com.fitfit.server.api.workout.dto.*;
import com.fitfit.server.api.workout.repository.ExerciseRecordRepository;
import com.fitfit.server.api.workout.repository.ExerciseSetRepository;
import com.fitfit.server.api.workout.repository.ExerciseTypeRepository;
import com.fitfit.server.global.auth.JwtTokenUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRecordRepository exerciseRecordRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    public void saveRecord(String token, ExerciseRecordRequest request) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        Member member = memberRepository.findById(userId)
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
    public void deleteRecord(String token, Long recordId) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        ExerciseRecord record = exerciseRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("ExerciseRecord not found"));

        if (!record.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this record");
        }

        exerciseRecordRepository.delete(record);
    }

    public void deleteSet(String token, Long setId, Long recordId) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

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

    public void updateSet(String token, Long setId, Long recordId, SetRequest request) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

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

    public ExerciseRecordResponse getRecordByrecordId(String token, Long recordId) {
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExerciseRecord record = exerciseRecordRepository.findByRecordId(recordId)
                .orElseThrow(() -> new RuntimeException("ExerciseRecord not found"));

        List<ExerciseSet> sets = exerciseSetRepository.findExerciseSetsByRecordId(record.getRecordId());

        ExerciseRecordResponse response = new ExerciseRecordResponse();
        response.setRecordId(record.getRecordId());

        Map<String, ExerciseResponse> exerciseMap = new HashMap<>();

        for (ExerciseSet s : sets) {
            String exerciseName = s.getExerciseType().getName().name();

            ExerciseResponse exerciseResponse = exerciseMap.getOrDefault(exerciseName, new ExerciseResponse());
            exerciseResponse.setName(exerciseName);

            SetResponse setResponse = new SetResponse();
            setResponse.setSetId(s.getSetId());

            if (exerciseResponse.getSets() == null) {
                exerciseResponse.setSets(new ArrayList<>());
            }
            exerciseResponse.getSets().add(setResponse);

            exerciseMap.put(exerciseName, exerciseResponse);
        }

        response.setExercises(new ArrayList<>(exerciseMap.values()));

        return response;
    }
}