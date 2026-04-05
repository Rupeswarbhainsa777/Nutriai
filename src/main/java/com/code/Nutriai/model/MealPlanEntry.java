package com.code.Nutriai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meal_plan_entries")
public class MealPlanEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String day; // e.g., Monday, Tuesday

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    // Many entries belong to one meal plan
    @ManyToOne
    @JoinColumn(name = "meal_plan_id", nullable = false)
    @JsonIgnore
    private MealPlan mealPlan;

    // Each entry is linked to one recipe
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;


    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER,
        SNACK
    }
}