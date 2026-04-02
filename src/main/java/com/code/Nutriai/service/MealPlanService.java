package com.code.Nutriai.service;

import com.code.Nutriai.model.MealPlan;
import com.code.Nutriai.model.User;
import com.code.Nutriai.repository.MealPlanEntryRepository;
import com.code.Nutriai.repository.MealPlanRepository;
import com.code.Nutriai.repository.RecipeRepository;
import com.code.Nutriai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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


    public MealPlan createMealPlan(Long id, LocalDate localDate){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));

        MealPlan mealPlan = new MealPlan();
        mealPlan.setUser(user);
        mealPlan.setWeekStartDate(localDate);
        return mealPlanRepository.save(mealPlan);
    }


    public MealPlan getMealPlanById(Long id){

        return mealPlanRepository.findById(id).orElseThrow(()->new RuntimeException("Meal plan not found"));
    }


}
