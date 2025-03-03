package com.fitfit.server.api.workout.controller;

import com.fitfit.server.api.workout.dto.ExerciseRecordRequest;
import com.fitfit.server.api.workout.dto.ExerciseRecordResponse;
import com.fitfit.server.api.workout.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exercise-records")
@RequiredArgsConstructor
public class ExerciseRecordController {
    private final ExerciseService exerciseService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRecord(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ExerciseRecordRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            exerciseService.saveRecord(token, request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Exercise record saved successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save exercise record");
        }
    }

    @DeleteMapping("/delete/{recordId}")
    public ResponseEntity<String> deleteExerciseRecord(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long recordId) {
        String token = authorizationHeader.replace("Bearer ", "");
        exerciseService.deleteRecord(token, recordId);
        return ResponseEntity.ok("Exercise record deleted successfully");
    }

    /*
    @GetMapping("/{userId}")
    public ExerciseRecordResponse getRecordByDate(
            @PathVariable Long userId,
            @RequestParam LocalDate date) {
        return exerciseService.getRecordByDate(userId, date);
    }
    */
}

