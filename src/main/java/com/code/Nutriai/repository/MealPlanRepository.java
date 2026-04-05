package com.code.Nutriai.repository;

import com.code.Nutriai.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan,Long> {
    // used in duplicate check above
    boolean existsByUserIdAndWeekStartDate(Long userId, LocalDate weekStartDate);

    // useful for frontend — fetch plan by user + week
    Optional<MealPlan> findByUserIdAndWeekStartDate(Long userId, LocalDate weekStartDate);
}

