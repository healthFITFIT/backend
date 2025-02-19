package com.fitfit.server.workoutTest;

import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.domain.ExerciseSet;
import com.fitfit.server.api.workout.repository.ExerciseRecordRepository;
import com.fitfit.server.api.workout.repository.ExerciseSetRepository;
import com.fitfit.server.api.workout.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    @Mock
    private ExerciseRecordRepository exerciseRecordRepository;

    @Mock
    private ExerciseSetRepository exerciseSetRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    private ExerciseRecord record;
    private ExerciseSet set;

    @BeforeEach
    void setUp() {
        record = new ExerciseRecord();
        record.setRecordId(1L);
        record.setUserId(1L);
        record.setDuration(LocalTime.parse("00:30:00"));
        record.setCreatedAt(LocalDate.parse("2024-02-16"));

        set = new ExerciseSet();
        set.setSetId(1L);
        set.setExerciseRecord(record);
        set.setReps(10);
        set.setWeight(20.0f);
    }

    @Test
    void testGetAllRecords() {
        when(exerciseRecordRepository.findAll()).thenReturn(Arrays.asList(record));
        List<ExerciseRecord> records = exerciseService.getAllRecords();
        assertFalse(records.isEmpty());
        assertEquals(1, records.size());
    }

    @Test
    void testGetRecordById() {
        when(exerciseRecordRepository.findById(1L)).thenReturn(Optional.of(record));
        Optional<ExerciseRecord> foundRecord = exerciseService.getRecordById(1L);
        assertTrue(foundRecord.isPresent());
        assertEquals(1L, foundRecord.get().getRecordId());
    }

    @Test
    void testSaveRecord() {
        when(exerciseRecordRepository.save(record)).thenReturn(record);
        ExerciseRecord savedRecord = exerciseService.saveRecord(record);
        assertNotNull(savedRecord);
        assertEquals(1L, savedRecord.getRecordId());
    }

    @Test
    void testDeleteRecord() {
        doNothing().when(exerciseRecordRepository).deleteById(1L);
        exerciseService.deleteRecord(1L);
        verify(exerciseRecordRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllSets() {
        when(exerciseSetRepository.findAll()).thenReturn(Arrays.asList(set));
        List<ExerciseSet> sets = exerciseService.getAllSets();
        assertFalse(sets.isEmpty());
        assertEquals(1, sets.size());
    }

    @Test
    void testSaveSet() {
        when(exerciseSetRepository.save(set)).thenReturn(set);
        ExerciseSet savedSet = exerciseService.saveSet(set);
        assertNotNull(savedSet);
        assertEquals(1L, savedSet.getSetId());
    }

    @Test
    void testDeleteSet() {
        doNothing().when(exerciseSetRepository).deleteById(1L);
        exerciseService.deleteSet(1L);
        verify(exerciseSetRepository, times(1)).deleteById(1L);
    }
}
