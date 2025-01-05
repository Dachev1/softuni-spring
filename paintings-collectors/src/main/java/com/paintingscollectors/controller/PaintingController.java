package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.PaintingAddDTO;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.service.PaintingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/paintings")
public class PaintingController {

    private final UserSession userSession;
    private final PaintingService paintingService;

    public PaintingController(UserSession userSession, PaintingService paintingService) {
        this.userSession = userSession;
        this.paintingService = paintingService;
    }

    @GetMapping("/add")
    public String showAddPaintingPage(Model model) {
        model.addAttribute("paintingAddDTO", new PaintingAddDTO());
        model.addAttribute("styles", paintingService.getAllStyles());
        return "add-painting";
    }

    @PostMapping("/add")
    public String addPainting(@Valid @ModelAttribute("paintingAddDTO") PaintingAddDTO paintingAddDTO,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("styles", paintingService.getAllStyles());
            return "add-painting";
        }
        paintingService.addPainting(paintingAddDTO, userSession.getCurrentUser());
        return "redirect:/home";
    }

    @PostMapping("/remove/{id}")
    public String removePainting(@PathVariable Long id) {
        paintingService.removePainting(id, userSession.getCurrentUser());
        return "redirect:/home";
    }

    @PostMapping("/remove-favorites/{id}")
    public String removeFromFavorites(@PathVariable Long id) {
        paintingService.removeFavoritePainting(id, userSession.getCurrentUser());
        return "redirect:/home";
    }

    @PostMapping("/add-favorites/{id}")
    public String addFavorite(@PathVariable Long id) {
        paintingService.addToFavorites(id, userSession.getCurrentUser());
        return "redirect:/home";
    }

    @PostMapping("/vote/{id}")
    public String voteForPainting(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            paintingService.voteForPainting(id, userSession.getCurrentUser());
            redirectAttributes.addFlashAttribute("successMessage", "Vote successfully cast!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/home";
    }

    @GetMapping("/paintings/details/{id}")
    public String showPaintingDetails(@PathVariable Long id, Model model) {
        User currentUser = userSession.getCurrentUser();

        boolean hasVoted = paintingService.hasUserVoted(id, currentUser);
        model.addAttribute("hasVoted", hasVoted);
        return "painting-details";
    }

    @GetMapping("/hasUserVoted/{id}")
    @ResponseBody
    public boolean hasUserVoted(@PathVariable Long id) {
        return paintingService.hasUserVoted(id, userSession.getCurrentUser());
    }
}
