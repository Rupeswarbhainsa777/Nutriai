package com.code.Nutriai.dto;

import lombok.Data;

@Data
public class MealPlanRequest {
    private Long userId;
    private String weekStartDate;
}