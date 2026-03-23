package com.code.Nutriai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double quantity;

    private String unit;

    // Many ingredients belong to one recipe
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
}