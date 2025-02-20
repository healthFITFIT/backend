package com.fitfit.server.api.workout.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ExerciseRecordResponse {
    private Long recordId;
    private LocalDate createdAt;
    private Integer duration;
    private List<ExerciseResponse> exercises;
}
