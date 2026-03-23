package com.code.Nutriai.model;



import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_preferences")
public class UserPreference {

    @Id
    private Long userId;

    private String cuisineType;

    private String allergies;

    private String dislikedIngredients;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}