package com.fitfit.server.api.workout.domain;

import com.fitfit.server.api.user.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "exercise_records")
@Getter
@Setter
@NoArgsConstructor
public class ExerciseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private Member userId;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "exerciseRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSet> exerciseSets;

}