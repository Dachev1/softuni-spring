package com.paintingscollectors.controller.user;

import com.paintingscollectors.config.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paintings")
public class PaintingController {

    private final UserSession userSession;

    public PaintingController(UserSession userSession) {
        this.userSession = userSession;
    }

    @GetMapping("/add")
    public String showAddPaintingPage() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/users/login";
        }
        return "add-painting";
    }
}
