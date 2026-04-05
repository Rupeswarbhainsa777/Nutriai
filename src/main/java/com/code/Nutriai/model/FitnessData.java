package com.code.Nutriai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fitness_data")
public class FitnessData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate date;
    private int steps;
    private int caloriesBurned;
    private int activeMinutes;

    @Enumerated(EnumType.STRING)
    private WorkoutType workoutType;

    public enum WorkoutType {
        RUNNING,
        WALKING,
        CYCLING,
        SWIMMING,
        YOGA,
        STRENGTH,
        OTHER
    }
}