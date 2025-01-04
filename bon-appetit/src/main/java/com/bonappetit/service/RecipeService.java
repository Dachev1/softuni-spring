package com.bonappetit.service;

import com.bonappetit.dtos.RecipeAddDTO;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.emuns.CategoryName;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface RecipeService {
    void addRecipe(RecipeAddDTO recipeAddDTO);

    List<Recipe> getRecipesByCategory(String category);

    List<Recipe> getFavoritesForUser(Long userId);

    void addToFavorites(Long recipeId, Long userId);
}
