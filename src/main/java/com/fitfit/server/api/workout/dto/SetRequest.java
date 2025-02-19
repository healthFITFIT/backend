package com.fitfit.server.api.workout.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetRequest {
    private int reps;
    private Float weight;
}
