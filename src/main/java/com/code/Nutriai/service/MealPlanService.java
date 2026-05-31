package com.code.Nutriai.service;

import com.code.Nutriai.dto.DayMealDTO;
import com.code.Nutriai.dto.RecipeCardDTO;
import com.code.Nutriai.dto.WeeklyMealPlanDTO;
import com.code.Nutriai.model.MealPlan;
import com.code.Nutriai.model.MealPlanEntry;
import com.code.Nutriai.model.Recipe;
import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.MealPlanEntryRepository;
import com.code.Nutriai.repository.MealPlanRepository;
import com.code.Nutriai.repository.RecipeRepository;
import com.code.Nutriai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealPlanService {

    private final MealPlanEntryRepository mealPlanEntryRepository;
    private final MealPlanRepository mealPlanRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    // =========================
    // Create Meal Plan
    // =========================

    public ResponseEntity<?> createMealPlan(Long userId,
                                            LocalDate weekStartDate) {

        try {

            User user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "User not found with id: "
                                            + userId));

            boolean exists =
                    mealPlanRepository
                            .existsByUserIdAndWeekStartDate(
                                    userId,
                                    weekStartDate
                            );

            if (exists) {

                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Meal plan already exists for this week");
            }

            MealPlan mealPlan = new MealPlan();

            mealPlan.setUser(user);
            mealPlan.setWeekStartDate(weekStartDate);

            MealPlan saved =
                    mealPlanRepository.save(mealPlan);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(saved);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // =========================
    // Get Meal Plan By Id
    // =========================

    public ResponseEntity<?> getMealPlanById(Long mealPlanId) {

        try {

            MealPlan mealPlan =
                    mealPlanRepository.findById(mealPlanId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Meal plan not found"));

            return ResponseEntity.ok(mealPlan);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // =========================
    // Get All Entries
    // =========================

    public ResponseEntity<?> getEntries(Long mealPlanId) {

        List<MealPlanEntry> entries =
                mealPlanEntryRepository
                        .findByMealPlanId(mealPlanId);

        return ResponseEntity.ok(entries);
    }

    // =========================
    // Add Or Update Entry
    // =========================

    public ResponseEntity<?> addOrUpdateEntry(
            Long mealPlanId,
            LocalDate mealDate,
            MealPlanEntry.MealType mealType,
            Long recipeId) {

        try {

            MealPlan mealPlan =
                    mealPlanRepository.findById(mealPlanId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Meal plan not found"));

            Recipe recipe =
                    recipeRepository.findById(recipeId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Recipe not found"));

            MealPlanEntry entry =
                    mealPlanEntryRepository
                            .findByMealPlanIdAndMealDateAndMealType(
                                    mealPlanId,
                                    mealDate,
                                    mealType
                            )
                            .orElse(null);

            if (entry == null) {

                entry = new MealPlanEntry();

                entry.setMealPlan(mealPlan);
                entry.setMealDate(mealDate);
                entry.setMealType(mealType);
            }

            entry.setRecipe(recipe);

            MealPlanEntry saved =
                    mealPlanEntryRepository.save(entry);

            return ResponseEntity.ok(saved);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // =========================
    // Delete Entry
    // =========================

    public ResponseEntity<?> deleteEntry(
            Long mealPlanId,
            LocalDate mealDate,
            MealPlanEntry.MealType mealType) {

        try {

            MealPlanEntry entry =
                    mealPlanEntryRepository
                            .findByMealPlanIdAndMealDateAndMealType(
                                    mealPlanId,
                                    mealDate,
                                    mealType
                            )
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Entry not found"));

            mealPlanEntryRepository.delete(entry);

            return ResponseEntity.ok(
                    "Entry deleted successfully");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // =========================
    // Delete Meal Plan
    // =========================

    public ResponseEntity<?> deleteMealPlan(Long mealPlanId) {

        try {

            mealPlanRepository.deleteById(mealPlanId);

            return ResponseEntity.ok(
                    "Meal plan deleted successfully");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // =========================
    // Weekly Planner View
    // =========================

    public WeeklyMealPlanDTO getWeeklyView(Long mealPlanId) {

        MealPlan mealPlan =
                mealPlanRepository.findById(mealPlanId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Meal plan not found"));

        List<MealPlanEntry> entries =
                mealPlanEntryRepository
                        .findByMealPlanId(mealPlanId);

        WeeklyMealPlanDTO response =
                new WeeklyMealPlanDTO();

        response.setMealPlanId(mealPlan.getId());
        response.setWeekStartDate(
                mealPlan.getWeekStartDate());

        List<DayMealDTO> days =
                new ArrayList<>();

        for (int i = 0; i < 7; i++) {

            LocalDate currentDate =
                    mealPlan.getWeekStartDate()
                            .plusDays(i);

            DayMealDTO dayDTO =
                    new DayMealDTO();

            dayDTO.setDate(currentDate);

            int totalCalories = 0;

            for (MealPlanEntry entry : entries) {

                if (entry == null) {
                    continue;
                }

                LocalDate mealDate =
                        entry.getMealDate();

                if (mealDate == null) {

                    System.out.println(
                            "Skipping invalid entry with null mealDate. Entry ID = "
                                    + entry.getId());

                    continue;
                }

                if (!currentDate.equals(mealDate)) {
                    continue;
                }

                Recipe recipe =
                        entry.getRecipe();

                if (recipe == null) {

                    System.out.println(
                            "Skipping invalid entry with null recipe. Entry ID = "
                                    + entry.getId());

                    continue;
                }

                MealPlanEntry.MealType mealType =
                        entry.getMealType();

                if (mealType == null) {

                    System.out.println(
                            "Skipping invalid entry with null mealType. Entry ID = "
                                    + entry.getId());

                    continue;
                }

                RecipeCardDTO recipeDTO =
                        new RecipeCardDTO(
                                recipe.getId(),
                                recipe.getName(),
                                recipe.getCalories()
                        );

                totalCalories += recipe.getCalories();

                switch (mealType) {

                    case BREAKFAST ->
                            dayDTO.setBreakfast(recipeDTO);

                    case LUNCH ->
                            dayDTO.setLunch(recipeDTO);

                    case DINNER ->
                            dayDTO.setDinner(recipeDTO);

                    case SNACK ->
                            dayDTO.setSnack(recipeDTO);
                }
            }

            dayDTO.setTotalCalories(totalCalories);

            days.add(dayDTO);
        }

        response.setDays(days);

        return response;
    }
}