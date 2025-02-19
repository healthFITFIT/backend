package com.fitfit.server.api.workout.controller;

import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.domain.ExerciseSet;
import com.fitfit.server.api.workout.domain.ExerciseType;
import com.fitfit.server.api.workout.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/records")
    public List<ExerciseRecord> getAllRecords() {
        return exerciseService.getAllRecords();
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<ExerciseRecord> getRecordById(@PathVariable Long id) {
        return exerciseService.getRecordById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/records")
    public ExerciseRecord createRecord(@RequestBody ExerciseRecord record) {
        return exerciseService.saveRecord(record);
    }

    @DeleteMapping("/records/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        exerciseService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sets")
    public List<ExerciseSet> getAllSets() {
        return exerciseService.getAllSets();
    }

    @PostMapping("/sets")
    public ExerciseSet createSet(@RequestBody ExerciseSet set) {
        return exerciseService.saveSet(set);
    }

    @DeleteMapping("/sets/{id}")
    public ResponseEntity<Void> deleteSet(@PathVariable Long id) {
        exerciseService.deleteSet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    public List<ExerciseType> getAllExerciseTypes() {
        return exerciseService.getAllExerciseTypes();
    }
}

