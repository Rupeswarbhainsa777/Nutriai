package com.code.Nutriai.repository;

import com.code.Nutriai.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealPlanRepository extends JpaRepository<MealPlan,Long> {
}
