package com.fitfit.server.api.workout.service;

import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.domain.ExerciseSet;
import com.fitfit.server.api.workout.domain.ExerciseType;
import com.fitfit.server.api.workout.repository.ExerciseRecordRepository;
import com.fitfit.server.api.workout.repository.ExerciseSetRepository;
import com.fitfit.server.api.workout.repository.ExerciseTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    private final ExerciseRecordRepository exerciseRecordRepository;
    private final ExerciseSetRepository exerciseSetRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    public ExerciseService(ExerciseRecordRepository exerciseRecordRepository,
                           ExerciseSetRepository exerciseSetRepository,
                           ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseRecordRepository = exerciseRecordRepository;
        this.exerciseSetRepository = exerciseSetRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public List<ExerciseRecord> getRecordsByUserId(Long userId) {
        return exerciseRecordRepository.findByUserId(userId);
    }

    public List<ExerciseRecord> getAllRecords() {
        return exerciseRecordRepository.findAll();
    }

    public Optional<ExerciseRecord> getRecordById(Long id) {
        return exerciseRecordRepository.findById(id);
    }

    public ExerciseRecord saveRecord(ExerciseRecord record) {
        return exerciseRecordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        exerciseRecordRepository.deleteById(id);
    }

    public List<ExerciseSet> getAllSets() {
        return exerciseSetRepository.findAll();
    }

    public ExerciseSet saveSet(ExerciseSet set) {
        return exerciseSetRepository.save(set);
    }

    public void deleteSet(Long id) {
        exerciseSetRepository.deleteById(id);
    }

    public List<ExerciseType> getAllExerciseTypes() {
        return exerciseTypeRepository.findAll();
    }
}