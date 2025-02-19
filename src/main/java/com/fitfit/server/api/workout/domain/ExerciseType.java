package com.fitfit.server.api.workout.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExerciseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    @Enumerated(EnumType.STRING)
    private ExerciseName name;
}

enum ExerciseName {
    SQUAT, PULL_UP, PUSH_UP, LATERAL_RAISE;
}
