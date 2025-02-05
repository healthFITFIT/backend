package com.example.fitfit.controller;

import com.example.fitfit.domain.ExerciseRecord;
import com.example.fitfit.service.ExerciseRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/exercise-records")
public class ExerciseRecordController {
    private final ExerciseRecordService exerciseRecordService;

    public ExerciseRecordController(ExerciseRecordService exerciseRecordService){
        this.exerciseRecordService = exerciseRecordService;
    }

    @GetMapping
    public List<ExerciseRecord> getAllRecords() {
        return exerciseRecordService.getAllRecords();
    }

    @GetMapping("/{id}")
    public ExerciseRecord getRecordById(@PathVariable Long id) {
        return exerciseRecordService.getRecordById(id);
    }

    @PostMapping
    public ExerciseRecord createRecord(@RequestBody ExerciseRecord record) {
        return exerciseRecordService.createRecord(record);
    }

    @PutMapping("/{id}")
    public ExerciseRecord updateRecord(@PathVariable Long id, @RequestBody ExerciseRecord record) {
        return exerciseRecordService.updateRecord(id, record);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id) {
        exerciseRecordService.deleteRecord(id);
    }

}
