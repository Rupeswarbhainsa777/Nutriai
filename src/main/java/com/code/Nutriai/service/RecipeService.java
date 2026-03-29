
package com.code.Nutriai.service;

import com.code.Nutriai.model.Recipe;
import com.code.Nutriai.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    // ✅ Get all recipes
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // ✅ Get recipe by ID
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.orElse(null);
    }

    // ✅ Save new recipe
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    // ✅ Update recipe
    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        Recipe existing = recipeRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(updatedRecipe.getName());
            existing.setDescription(updatedRecipe.getDescription());
            existing.setImageUrl(updatedRecipe.getImageUrl());
            existing.setCalories(updatedRecipe.getCalories());
            existing.setProtein(updatedRecipe.getProtein());
            existing.setCarbs(updatedRecipe.getCarbs());

            return recipeRepository.save(existing);
        }

        return null;
    }

    // ✅ Delete recipe
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}