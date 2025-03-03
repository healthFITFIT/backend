package com.fitfit.server.api.workout.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExerciseDetails {
    private String name; // 운동 이름
    private List<SetDetails> sets;
}
