package com.bonappetit.controller;

import com.bonappetit.config.UserSession;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final RecipeService recipeService;
    private final UserSession userSession;

    public HomeController(RecipeService recipeService, UserSession userSession) {
        this.recipeService = recipeService;
        this.userSession = userSession;
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        model.addAttribute("desserts", recipeService.getRecipesByCategory("DESSERT"));
        model.addAttribute("mainDishes", recipeService.getRecipesByCategory("MAIN_DISH"));
        model.addAttribute("cocktails", recipeService.getRecipesByCategory("COCKTAIL"));
        model.addAttribute("favorites", recipeService.getFavoritesForUser(userSession.getUserId()));

        return "home";
    }

    @GetMapping("/add-to-favorites")
    public String addToFavorites(@RequestParam Long recipeId) {
        recipeService.addToFavorites(recipeId, userSession.getUserId());
        return "redirect:/home";
    }
}
