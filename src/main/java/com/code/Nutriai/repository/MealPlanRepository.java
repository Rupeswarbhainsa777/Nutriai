
package com.code.Nutriai.repository;

import com.code.Nutriai.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MealPlanRepository
        extends JpaRepository<MealPlan, Long> {

    boolean existsByUserIdAndWeekStartDate(
            Long userId,
            LocalDate weekStartDate
    );
}