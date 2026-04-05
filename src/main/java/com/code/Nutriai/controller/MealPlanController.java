package com.code.Nutriai.controller;


import com.code.Nutriai.dto.MealPlanRequest;
import com.code.Nutriai.model.MealPlanEntry;
import com.code.Nutriai.service.MealPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/meal-plan")
public class MealPlanController {


    private final MealPlanService mealPlanService;


    @PostMapping("/create")
    public ResponseEntity<?> createMealPlan(@RequestBody MealPlanRequest request) {
        return mealPlanService.createMealPlan(
                request.getUserId(),
                java.time.LocalDate.parse(request.getWeekStartDate())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMealPlanById(@PathVariable  Long id){
        return mealPlanService.getMealPlanById(id);
    }
    @PostMapping("/{mealPlanId}/entry")
    public ResponseEntity<?> addOrUpdateEntry(
            @PathVariable Long mealPlanId,
            @RequestParam String day,
            @RequestParam MealPlanEntry.MealType mealType,
            @RequestParam Long recipeId){
        return mealPlanService.addOrUpdateEntry(mealPlanId,day,mealType,recipeId);
    }


}
