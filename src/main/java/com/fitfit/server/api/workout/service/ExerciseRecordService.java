package com.fitfit.server.api.workout.service;

import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.repository.ExerciseRecordRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExerciseRecordService {
    private final ExerciseRecordRepository exerciseRecordRepository;

    public ExerciseRecordService(ExerciseRecordRepository recordRepository){
        this.exerciseRecordRepository = recordRepository;
    }

    public List<ExerciseRecord> getAllRecords() {
        return exerciseRecordRepository.findAll();
    }

    public ExerciseRecord getRecordById(Long id) {
        return exerciseRecordRepository.findById(id).orElse(null);
    }

    public ExerciseRecord createRecord(ExerciseRecord record) {
        return exerciseRecordRepository.save(record);
    }

    public ExerciseRecord updateRecord(Long id, ExerciseRecord updatedRecord) {
        if (exerciseRecordRepository.existsById(id)) {
            updatedRecord.setExerciseRecordId(id);
            return exerciseRecordRepository.save(updatedRecord);
        }
        return null;
    }

    public void deleteRecord(Long id) {
        exerciseRecordRepository.deleteById(id);
    }
}