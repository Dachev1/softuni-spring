package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.service.PaintingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserSession userSession;
    private final PaintingService paintingService;

    public HomeController(UserSession userSession, PaintingService paintingService) {
        this.userSession = userSession;
        this.paintingService = paintingService;
    }

    @GetMapping("/")
    public String showIndexPage() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        User currentUser = userSession.getCurrentUser();
        model.addAttribute("myPaintings", paintingService.getPaintingsByOwner(currentUser));
        model.addAttribute("myFavorites", paintingService.getFavoritePaintings(currentUser));
        model.addAttribute("otherPaintings", paintingService.getOtherPaintings(currentUser));
        model.addAttribute("mostRatedPaintings", paintingService.getMostRatedPaintings());
        return "home";
    }
}
