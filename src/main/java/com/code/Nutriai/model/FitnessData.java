package com.code.Nutriai.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonIgnoreProperties({"password", "email"}) // prevent unnecessary data
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

    private Integer steps;
    private Integer caloriesBurned;
    private Integer activeMinutes;

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