package com.fitfit.server.api.workout.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveRecordResponse {
    private Long recordId;
    private List<ExerciseResponse> exercises;
}
