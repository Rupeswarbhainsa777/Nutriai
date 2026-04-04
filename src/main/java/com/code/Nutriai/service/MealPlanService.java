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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MealPlanService {


    @Autowired
    private final MealPlanEntryRepository mealPlanEntryRepository;
    @Autowired
    private final MealPlanRepository mealPlanRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RecipeRepository recipeRepository;


    public MealPlan createMealPlan(Long id, LocalDate localDate) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        MealPlan mealPlan = new MealPlan();
        mealPlan.setUser(user);
        mealPlan.setWeekStartDate(localDate);
        return mealPlanRepository.save(mealPlan);
    }


    public MealPlan getMealPlanById(Long id) {

        return mealPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("Meal plan not found"));
    }

    public List<MealPlanEntry> getEntries(Long mealPlanId) {
        return mealPlanEntryRepository.findByMealPlanId(mealPlanId);
    }

    public MealPlanEntry addOrUpdateEntry(Long mealPlanId,
                                          String day,
                                          MealPlanEntry.MealType mealType,
                                          Long recipeId) {

        MealPlan mealPlan = mealPlanRepository.findById(mealPlanId).
                orElseThrow(() -> new RuntimeException("Meal plan not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        MealPlanEntry mealPlanEntry = mealPlanEntryRepository.findByMealPlanIdAndDayAndMealType(mealPlanId, day, mealType)
                .orElse(null);

        if (mealPlanEntry == null) {
            mealPlanEntry = new MealPlanEntry();
            mealPlanEntry.setMealPlan(mealPlan);
            mealPlanEntry.setDay(day);
            mealPlanEntry.setMealType(mealType);
        }
        mealPlanEntry.setRecipe(recipe);
        return mealPlanEntryRepository.save(mealPlanEntry);

    }


    public void deleteEntry(Long mealPlanId,
                            String day,
                            MealPlanEntry.MealType mealType,
                            Long recipeId) {

        MealPlanEntry entry = mealPlanEntryRepository
                .findByMealPlanIdAndDayAndMealType(mealPlanId, day, mealType)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        mealPlanEntryRepository.delete(entry);
    }


    // Delete full meal plan
    private void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }


}
