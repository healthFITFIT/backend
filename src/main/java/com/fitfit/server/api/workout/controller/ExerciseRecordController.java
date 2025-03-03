package com.fitfit.server.api.workout.controller;

import com.fitfit.server.api.workout.dto.ExerciseRecordDetailsResponse;
import com.fitfit.server.api.workout.dto.ExerciseRecordRequest;
import com.fitfit.server.api.workout.dto.SaveRecordResponse;
import com.fitfit.server.api.workout.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercise-records")
@RequiredArgsConstructor
public class ExerciseRecordController {
    private final ExerciseService exerciseService;

    @PostMapping("/save")
    public ResponseEntity<?> saveRecord(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ExerciseRecordRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");

        try {
            SaveRecordResponse response = exerciseService.saveRecord(token, request);
            return ResponseEntity.ok(response);
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


    @GetMapping("/{recordId}")
    public ExerciseRecordDetailsResponse getRecordByDate(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long recordId) {
        String token = authorizationHeader.replace("Bearer ", "");
        return exerciseService.getRecordByrecordId(token, recordId);
    }

}

