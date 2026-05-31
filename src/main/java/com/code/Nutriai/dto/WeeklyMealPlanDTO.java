package com.code.Nutriai.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WeeklyMealPlanDTO {

    private Long mealPlanId;

    private LocalDate weekStartDate;

    private List<DayMealDTO> days;
}