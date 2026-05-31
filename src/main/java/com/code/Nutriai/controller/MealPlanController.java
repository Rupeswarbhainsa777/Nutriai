package com.code.Nutriai.controller;

import com.code.Nutriai.dto.AddEntryRequestDTO;
import com.code.Nutriai.service.MealPlanService;
import com.code.Nutriai.model.MealPlanEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/meal-plans")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @PostMapping("/create")
    public ResponseEntity<?> createMealPlan(
            @RequestParam Long userId,
            @RequestParam LocalDate weekStartDate) {

        return mealPlanService.createMealPlan(
                userId,
                weekStartDate
        );
    }

    @GetMapping("/{mealPlanId}")
    public ResponseEntity<?> getMealPlan(
            @PathVariable Long mealPlanId) {

        return mealPlanService.getMealPlanById(mealPlanId);
    }

    @GetMapping("/{mealPlanId}/entries")
    public ResponseEntity<?> getEntries(
            @PathVariable Long mealPlanId) {

        return mealPlanService.getEntries(mealPlanId);
    }

    @PostMapping("/{mealPlanId}/entries")
    public ResponseEntity<?> addOrUpdateEntry(
            @PathVariable Long mealPlanId,
            @RequestBody AddEntryRequestDTO request) {

        return mealPlanService.addOrUpdateEntry(
                mealPlanId,
                request.getMealDate(),
                request.getMealType(),
                request.getRecipeId()
        );
    }

    @DeleteMapping("/{mealPlanId}/entries")
    public ResponseEntity<?> deleteEntry(
            @PathVariable Long mealPlanId,
            @RequestParam LocalDate mealDate,
            @RequestParam MealPlanEntry.MealType mealType) {

        return mealPlanService.deleteEntry(
                mealPlanId,
                mealDate,
                mealType
        );
    }

    @DeleteMapping("/{mealPlanId}")
    public ResponseEntity<?> deleteMealPlan(
            @PathVariable Long mealPlanId) {

        return mealPlanService.deleteMealPlan(mealPlanId);
    }

    @GetMapping("/{mealPlanId}/weekly-view")
    public ResponseEntity<?> getWeeklyView(
            @PathVariable Long mealPlanId) {

        return ResponseEntity.ok(
                mealPlanService.getWeeklyView(mealPlanId)
        );
    }
}