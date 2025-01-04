package com.bonappetit.controller;

import com.bonappetit.config.UserSession;
import com.bonappetit.dtos.RecipeAddDTO;
import com.bonappetit.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserSession userSession;

    public RecipeController(RecipeService recipeService, UserSession userSession) {
        this.recipeService = recipeService;
        this.userSession = userSession;
    }

    @GetMapping("/add")
    public String showAddRecipePage(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/users/login";
        }
        if (!model.containsAttribute("recipeAddDTO")) {
            model.addAttribute("recipeAddDTO", new RecipeAddDTO());
        }
        return "recipe-add";
    }

    @PostMapping("/add")
    public String addRecipe(@Valid @ModelAttribute("recipeAddDTO") RecipeAddDTO recipeAddDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/users/login";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeAddDTO", recipeAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeAddDTO", bindingResult);
            return "redirect:/recipes/add";
        }

        try {
            recipeService.addRecipe(recipeAddDTO);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add recipe. Please try again.");
            return "redirect:/recipes/add";
        }

        return "redirect:/home";
    }
}

