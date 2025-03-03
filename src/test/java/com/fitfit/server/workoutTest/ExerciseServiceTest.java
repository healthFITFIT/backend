package com.fitfit.server.workoutTest;

import com.fitfit.server.api.user.Member;
import com.fitfit.server.api.user.repository.MemberRepository;
import com.fitfit.server.api.workout.domain.ExerciseName;
import com.fitfit.server.api.workout.domain.ExerciseRecord;
import com.fitfit.server.api.workout.domain.ExerciseSet;
import com.fitfit.server.api.workout.domain.ExerciseType;
import com.fitfit.server.api.workout.dto.*;
import com.fitfit.server.api.workout.repository.ExerciseRecordRepository;
import com.fitfit.server.api.workout.repository.ExerciseSetRepository;
import com.fitfit.server.api.workout.repository.ExerciseTypeRepository;
import com.fitfit.server.api.workout.service.ExerciseService;
import com.fitfit.server.global.auth.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @InjectMocks
    private ExerciseService exerciseService;

    @Mock
    private ExerciseRecordRepository exerciseRecordRepository;

    @Mock
    private ExerciseSetRepository exerciseSetRepository;

    @Mock
    private ExerciseTypeRepository exerciseTypeRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;  // Mock the JwtTokenUtil

    @BeforeEach
    void setUp() {
        exerciseService = new ExerciseService(
                exerciseRecordRepository,
                exerciseSetRepository,
                exerciseTypeRepository,
                memberRepository,
                jwtTokenUtil
        );
    }

    @Test
    void saveRecord_shouldSaveSuccessfully() {
        // Given
        String token = "mockToken";
        Long userId = 1L;
        ExerciseRecordRequest request = new ExerciseRecordRequest();
        request.setDate(LocalDate.now());
        request.setDuration(360);

        ExerciseRequest exerciseRequest = new ExerciseRequest();
        exerciseRequest.setName("SQUAT");

        SetRequest setRequest = new SetRequest();
        setRequest.setReps(10);
        setRequest.setWeight(20.0f);

        exerciseRequest.setSets(List.of(setRequest));
        request.setExercises(List.of(exerciseRequest));

        Member member = Mockito.mock(Member.class);

        ExerciseType exerciseType = new ExerciseType();

        when(jwtTokenUtil.getUserIdFromToken(token)).thenReturn(userId);  // Mocking the token parsing
        when(memberRepository.findById(userId)).thenReturn(Optional.of(member));
        when(exerciseTypeRepository.findByName("SQUAT")).thenReturn(Optional.of(exerciseType));

        // When
        exerciseService.saveRecord(token, request);

        // Then
        verify(exerciseRecordRepository, times(1)).save(any(ExerciseRecord.class));
        verify(exerciseSetRepository, times(1)).save(any(ExerciseSet.class));
    }

    @Test
    void deleteRecord_shouldDeleteSuccessfully() {
        // Given
        String token = "mockToken";
        Long userId = 1L;
        Long recordId = 1L;

        Member member = Mockito.mock(Member.class);
        when(jwtTokenUtil.getUserIdFromToken(token)).thenReturn(userId);  // Mocking the token parsing
        when(member.getUserId()).thenReturn(userId);

        ExerciseRecord record = new ExerciseRecord();
        record.setRecordId(recordId);
        record.setUser(member);

        when(exerciseRecordRepository.findById(recordId)).thenReturn(Optional.of(record));

        // When
        exerciseService.deleteRecord(token, recordId);

        // Then
        verify(exerciseRecordRepository, times(1)).delete(record);
    }

    @Test
    void deleteSet_shouldDeleteExerciseSet() {
        // Given
        String token = "mockToken";
        Long userId = 1L;
        Long recordId = 1L;
        Long setId = 1L;

        Member member = Mockito.mock(Member.class);
        when(jwtTokenUtil.getUserIdFromToken(token)).thenReturn(userId);  // Mocking the token parsing
        when(member.getUserId()).thenReturn(userId);

        ExerciseRecord record = new ExerciseRecord();
        record.setRecordId(recordId);
        record.setUser(member);

        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setSetId(setId);
        exerciseSet.setExerciseRecord(record);

        when(exerciseSetRepository.findById(setId)).thenReturn(Optional.of(exerciseSet));

        // When
        exerciseService.deleteSet(token, setId, recordId);

        // Then
        verify(exerciseSetRepository, times(1)).delete(exerciseSet);
    }

    /**
     * 세트 수정 테스트
     */
    @Test
    void updateSet_shouldUpdateExerciseSet() {
        // Given
        String token = "mockToken";
        Long userId = 1L;
        Long recordId = 1L;
        Long setId = 1L;

        Member member = Mockito.mock(Member.class);
        when(jwtTokenUtil.getUserIdFromToken(token)).thenReturn(userId);  // Mocking the token parsing
        when(member.getUserId()).thenReturn(userId);

        ExerciseRecord record = new ExerciseRecord();
        record.setRecordId(recordId);
        record.setUser(member);

        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setSetId(setId);
        exerciseSet.setExerciseRecord(record);
        exerciseSet.setReps(10);
        exerciseSet.setWeight(20.0f);

        SetRequest updateRequest = new SetRequest();
        updateRequest.setReps(15);
        updateRequest.setWeight(25.0f);

        when(exerciseSetRepository.findById(setId)).thenReturn(Optional.of(exerciseSet));

        // When
        exerciseService.updateSet(token, setId, recordId, updateRequest);

        // Then
        assertEquals(15, exerciseSet.getReps());
        assertEquals(25.0f, exerciseSet.getWeight());
        verify(exerciseSetRepository, times(1)).save(exerciseSet);
    }
}