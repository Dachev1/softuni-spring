package com.paintingscollectors.controller;

import com.paintingscollectors.model.dto.AddPainting;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.service.PaintingService;
import com.paintingscollectors.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/paintings")
public class PaintingController {

    private final PaintingService paintingService;
    private final UserService userService;

    @Autowired
    public PaintingController(PaintingService paintingService, UserService userService) {
        this.paintingService = paintingService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public ModelAndView showAddPaintingPage() {
        ModelAndView mav = new ModelAndView("add-painting");
        mav.addObject("painting", new AddPainting());
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView addPainting(@Valid @ModelAttribute("painting") AddPainting addPainting, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors while adding painting: {}", bindingResult.getAllErrors());
            return new ModelAndView("add-painting");
        }

        UUID userId = (UUID) session.getAttribute("user_id");
        log.info("User {} is adding a new painting with details: {}", userId, addPainting);

        User owner = userService.getById(userId);
        Painting painting = paintingService.addPainting(addPainting, owner);
        log.info("Painting added successfully: {}", painting);

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView removePainting(@PathVariable("id") UUID paintingId, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        log.info("User {} requested removal of painting with id: {}", userId, paintingId);

        Painting removedPainting = paintingService.removePainting(paintingId, userId);
        log.info("Painting removed: {}", removedPainting);

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/add-favorites/{id}")
    public ModelAndView addToFavorites(@PathVariable("id") UUID paintingId, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        log.info("User {} requested to add painting {} to favorites", userId, paintingId);

        Painting favoritedPainting = paintingService.addToFavorites(paintingId, userId);
        log.info("Painting added to favorites: {}", favoritedPainting);

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/remove-favorites/{id}")
    public ModelAndView removeFromFavorites(@PathVariable("id") UUID paintingId, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        log.info("User {} requested to remove painting {} from favorites", userId, paintingId);

        Painting unfavoritedPainting = paintingService.removeFromFavorites(paintingId, userId);
        log.info("Painting removed from favorites: {}", unfavoritedPainting);

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/vote/{id}")
    public ModelAndView votePainting(@PathVariable("id") UUID paintingId, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        log.info("User {} requested to vote for painting {}", userId, paintingId);

        Painting votedPainting = paintingService.votePainting(paintingId, userId);
        log.info("Painting voted successfully: {}", votedPainting);

        return new ModelAndView("redirect:/home");
    }
}
