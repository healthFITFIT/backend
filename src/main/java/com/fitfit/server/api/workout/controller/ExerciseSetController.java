package com.fitfit.server.api.workout.controller;

import com.fitfit.server.api.workout.domain.ExerciseSet;
import com.fitfit.server.api.workout.service.ExerciseSetService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/sets")
public class ExerciseSetController {
    private final ExerciseSetService setService;

    public ExerciseSetController(ExerciseSetService setService) {
        this.setService = setService;
    }

    @GetMapping
    public List<ExerciseSet> getAllSets() {
        return setService.getAllSets();
    }

    @GetMapping("/{id}")
    public ExerciseSet getSetById(@PathVariable Long id) {
        return setService.getSetById(id);
    }

    @PostMapping
    public ExerciseSet createSet(@RequestBody ExerciseSet set) {
        return setService.createSet(set);
    }

    @PutMapping("/{id}")
    public ExerciseSet updateSet(@PathVariable Long id, @RequestBody ExerciseSet set) {
        return setService.updateSet(id, set);
    }

    @DeleteMapping("/{id}")
    public void deleteSet(@PathVariable Long id) {
        setService.deleteSet(id);
    }
}