package com.fitfit.server.api.workout.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExerciseRequest {
    private String name;
    private List<SetRequest> sets;
}
