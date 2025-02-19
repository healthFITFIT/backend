package com.fitfit.server.api.workout.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ExerciseRecordRequest {
    private Long userId;
    private LocalDate date;
    private int duration; // duration in seconds
    private List<ExerciseRequest> exercises;
}

