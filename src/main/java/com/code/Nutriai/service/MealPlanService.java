package com.code.Nutriai.service;

import com.code.Nutriai.model.MealPlan;
import com.code.Nutriai.model.MealPlanEntry;
import com.code.Nutriai.model.Recipe;
import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.MealPlanEntryRepository;
import com.code.Nutriai.repository.MealPlanRepository;
import com.code.Nutriai.repository.RecipeRepository;
import com.code.Nutriai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealPlanService {

    private final MealPlanEntryRepository mealPlanEntryRepository;
    private final MealPlanRepository mealPlanRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    // ✅ Create Meal Plan
    public ResponseEntity<?> createMealPlan(Long id, LocalDate localDate) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            // Check if a plan already exists for this week
            boolean exists = mealPlanRepository
                    .existsByUserIdAndWeekStartDate(id, localDate);
            if (exists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Meal plan already exists for this week");
            }

            MealPlan mealPlan = new MealPlan();
            mealPlan.setUser(user);
            mealPlan.setWeekStartDate(localDate);

            MealPlan saved = mealPlanRepository.save(mealPlan);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ Get Meal Plan
    public ResponseEntity<?> getMealPlanById(Long id) {
        try {
            MealPlan mealPlan = mealPlanRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Meal plan not found"));

            return ResponseEntity.ok(mealPlan);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ✅ Get Entries
    public ResponseEntity<?> getEntries(Long mealPlanId) {
        List<MealPlanEntry> entries = mealPlanEntryRepository.findByMealPlanId(mealPlanId);
        return ResponseEntity.ok(entries);
    }

    // ✅ Add or Update Entry
    public ResponseEntity<?> addOrUpdateEntry(Long mealPlanId,
                                              String day,
                                              MealPlanEntry.MealType mealType,
                                              Long recipeId) {
        try {
            MealPlan mealPlan = mealPlanRepository.findById(mealPlanId)
                    .orElseThrow(() -> new RuntimeException("Meal plan not found"));

            Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            MealPlanEntry entry = mealPlanEntryRepository
                    .findByMealPlanIdAndDayAndMealType(mealPlanId, day, mealType)
                    .orElse(null);

            if (entry == null) {
                entry = new MealPlanEntry();
                entry.setMealPlan(mealPlan);
                entry.setDay(day);
                entry.setMealType(mealType);
            }

            entry.setRecipe(recipe);

            return ResponseEntity.ok(mealPlanEntryRepository.save(entry));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ✅ Delete Entry
    public ResponseEntity<?> deleteEntry(Long mealPlanId,
                                         String day,
                                         MealPlanEntry.MealType mealType) {
        try {
            MealPlanEntry entry = mealPlanEntryRepository
                    .findByMealPlanIdAndDayAndMealType(mealPlanId, day, mealType)
                    .orElseThrow(() -> new RuntimeException("Entry not found"));

            mealPlanEntryRepository.delete(entry);

            return ResponseEntity.ok("Entry deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // ✅ Delete Meal Plan
    public ResponseEntity<?> deleteMealPlan(Long id) {
        try {
            mealPlanRepository.deleteById(id);
            return ResponseEntity.ok("Meal plan deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}