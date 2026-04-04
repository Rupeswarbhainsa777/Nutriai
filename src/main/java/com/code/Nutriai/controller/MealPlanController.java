package com.code.Nutriai.controller;


import com.code.Nutriai.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/mealplan")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;


}
