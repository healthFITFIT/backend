package com.fitfit.server.api.workout.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExerciseRecordDetailsResponse {
    private Long recordId;
    private int duration; // 운동 기록의 지속 시간
    private List<ExerciseDetails> exerciseRecords;
}
