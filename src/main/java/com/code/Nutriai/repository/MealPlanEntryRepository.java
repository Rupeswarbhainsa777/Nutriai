package com.code.Nutriai.repository;

import com.code.Nutriai.model.MealPlanEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealPlanEntryRepository
        extends JpaRepository<MealPlanEntry, Long> {

    List<MealPlanEntry> findByMealPlanId(Long mealPlanId);

    Optional<MealPlanEntry> findByMealPlanIdAndMealDateAndMealType(
            Long mealPlanId,
            LocalDate mealDate,
            MealPlanEntry.MealType mealType
    );
}