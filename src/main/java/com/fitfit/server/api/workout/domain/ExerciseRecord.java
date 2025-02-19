package com.fitfit.server.api.workout.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
<<<<<<< Updated upstream
import java.util.List;
=======

>>>>>>> Stashed changes

@Entity
@Table(name = "exercise_records")
@Getter
@Setter
@NoArgsConstructor
public class ExerciseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @Column(nullable = false)
    private Long userId;

<<<<<<< Updated upstream
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExerciseType exerciseType;

=======
>>>>>>> Stashed changes
    @Column(nullable = false)
    private LocalTime duration;

    @Column(nullable = false)
    private LocalDate createdAt;
}