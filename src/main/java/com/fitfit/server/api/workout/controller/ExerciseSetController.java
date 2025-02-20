package com.fitfit.server.api.workout.controller;

import com.fitfit.server.api.workout.dto.SetRequest;
import com.fitfit.server.api.workout.service.ExerciseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercise-records/{recordId}/sets")
@RequiredArgsConstructor
public class ExerciseSetController {
    private final ExerciseService exerciseService;

    //세트 삭제
    @DeleteMapping("/delete/{setId}")
    public ResponseEntity<String> deleteExerciseSet(
            @PathVariable Long recordId,
            @PathVariable Long setId,
            @RequestParam Long userId) {
        try {
            exerciseService.deleteSet(recordId, setId, userId);
            return ResponseEntity.ok("Exercise set deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update/{setId}")
    public ResponseEntity<String> updateExerciseSet(
            @PathVariable Long recordId,
            @PathVariable Long setId,
            @RequestBody SetRequest request,
            @RequestParam Long userId) {
        try {
            exerciseService.updateSet(setId, recordId, userId, request);
            return ResponseEntity.ok("Exercise set updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 세트가 존재하지 않음
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage()); // 권한 없음
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 세트가 다른 recordId에 속함
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}