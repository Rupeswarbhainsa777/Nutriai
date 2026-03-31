package com.code.Nutriai.controller;

import com.code.Nutriai.model.Recipe;
import com.code.Nutriai.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/get/{id}")
    public ResponseEntity<Recipe> get(@PathVariable Long id){

        Recipe recipe = recipeService.getRecipeById(id);

        if (recipe != null) {
            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Recipe>> getAll(){
        List<Recipe> all = recipeService.getAllRecipes();
        return ResponseEntity.ok(all);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id,@RequestBody Recipe recipe){

        Recipe recipe1 = recipeService.updateRecipe(id,recipe);

        return ResponseEntity.ok(recipe1);

    }

}
