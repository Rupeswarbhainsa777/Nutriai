package com.code.Nutriai.controller;

import com.code.Nutriai.model.Recipe;
import com.code.Nutriai.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/recipe")
public class RecipeController {


    @Autowired
    private RecipeService recipeService;

    @PostMapping("/add")
    public ResponseEntity<Recipe> save(@RequestBody Recipe recipe){
        Recipe saved = recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(saved);
    }
}
