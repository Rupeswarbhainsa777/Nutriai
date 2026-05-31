package com.code.Nutriai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCardDTO {

    private Long id;

    private String name;

    private int calories;
}