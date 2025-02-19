package com.fitfit.server.workoutTest;

import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.repository.ExerciseRecordRepository;
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

    @InjectMocks
    private ExerciseService exerciseService;

    private ExerciseRecord record;

    @BeforeEach
    void setUp() {
        record = new ExerciseRecord();
        record.setRecordId(1L);
        record.setUserId(1L);
        record.setDuration(LocalTime.parse("00:30:00"));
        record.setCreatedAt(LocalDate.parse("2024-02-16"));
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
}
