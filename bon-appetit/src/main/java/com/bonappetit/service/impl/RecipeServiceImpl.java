package com.bonappetit.service.impl;

import com.bonappetit.config.UserSession;
import com.bonappetit.dtos.RecipeAddDTO;
import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.model.entity.emuns.CategoryName;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipeRepository;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.service.RecipeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserSession userSession;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             UserRepository userRepository,
                             CategoryRepository categoryRepository,
                             UserSession userSession) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.userSession = userSession;
    }

    @Override
    public void addRecipe(RecipeAddDTO recipeAddDTO) {
        if (!userSession.isLoggedIn()) {
            throw new IllegalStateException("User is not logged in.");
        }

        User currentUser = userRepository.findById(userSession.getUserId())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Category category = categoryRepository.findByCategoryName(CategoryName.valueOf(recipeAddDTO.getCategory()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid category: " + recipeAddDTO.getCategory()));

        Recipe recipe = new Recipe();
        recipe.setName(recipeAddDTO.getName());
        recipe.setIngredients(recipeAddDTO.getIngredients());
        recipe.setCategory(category);
        recipe.setAddedBy(currentUser);

        recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findAllByCategory_CategoryName(CategoryName.valueOf(category));
    }

    @Override
    public List<Recipe> getFavoritesForUser(Long userId) {
        User user = userRepository.findByIdWithFavorites(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getFavouriteRecipes();
    }

    @Override
    @Transactional
    public void addToFavorites(Long recipeId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        user.getFavouriteRecipes().add(recipe);
        userRepository.save(user);
    }
}
