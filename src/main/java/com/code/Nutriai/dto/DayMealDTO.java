package com.code.Nutriai.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayMealDTO {

    private LocalDate date;

    private RecipeCardDTO breakfast;

    private RecipeCardDTO lunch;

    private RecipeCardDTO snack;

    private RecipeCardDTO dinner;

    private Integer totalCalories;
}