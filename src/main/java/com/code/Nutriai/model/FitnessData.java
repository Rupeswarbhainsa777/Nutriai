package com.code.Nutriai.model;



import jakarta.persistence.*;
import lombok.*;

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

    private LocalDate date;

    private int steps;

    private double caloriesBurned;

    private String workoutType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}