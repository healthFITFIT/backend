package com.fitfit.server.api.workout.service;

import com.fitfit.server.api.workout.domain.ExerciseSet;
import com.fitfit.server.api.workout.repository.ExerciseSetRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ExerciseSetService {
    private final ExerciseSetRepository setRepository;

    public ExerciseSetService(ExerciseSetRepository setRepository){
        this.setRepository = setRepository;
    }

    public List<ExerciseSet> getAllSets() {
        return setRepository.findAll();
    }

    public ExerciseSet getSetById(Long id) {
        return setRepository.findById(id).orElse(null);
    }

    public ExerciseSet createSet(ExerciseSet set) {
        return setRepository.save(set);
    }

    public ExerciseSet updateSet(Long id, ExerciseSet updatedSet) {
        if (setRepository.existsById(id)) {
            updatedSet.setSetId(id);
            return setRepository.save(updatedSet);
        }
        return null;
    }

    public void deleteSet(Long id) {
        setRepository.deleteById(id);
    }
}