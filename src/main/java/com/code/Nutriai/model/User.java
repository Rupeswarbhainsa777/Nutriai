package com.code.Nutriai.model;



import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String goal;

    private String dietaryRestrictions;

    private double height;
    private double weight;
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MealPlan> mealPlans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FitnessData> fitnessData;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserPreference preference;
}