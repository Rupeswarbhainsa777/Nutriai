package com.code.Nutriai.dto;

import com.code.Nutriai.model.MealPlanEntry;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddEntryRequestDTO {

    private LocalDate mealDate;

    private MealPlanEntry.MealType mealType;

    private Long recipeId;
}