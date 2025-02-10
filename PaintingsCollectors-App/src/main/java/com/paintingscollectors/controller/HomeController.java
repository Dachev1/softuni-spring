package com.paintingscollectors.controller;

import com.paintingscollectors.model.dto.HomePageView;
import com.paintingscollectors.service.PaintingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class HomeController {

    private final PaintingService paintingService;

    @Autowired
    public HomeController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping("/home")
    public ModelAndView showHomePage(HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        HomePageView homeModel = paintingService.getHomePageData(userId);
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("homeModel", homeModel);
        return mav;
    }
}
