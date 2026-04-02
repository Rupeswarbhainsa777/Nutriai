package com.code.Nutriai.repository;

import com.code.Nutriai.model.MealPlanEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealPlanEntryRepository  extends JpaRepository<MealPlanEntry,Long> {

    List<MealPlanEntry> findByMealPlanId(Long mealPlanId);

    Optional<MealPlanEntry> findByMealPlanIdAndDayAndMealType(
            Long mealPlanId,
            String day,
            MealPlanEntry.MealType mealType
    );
}
