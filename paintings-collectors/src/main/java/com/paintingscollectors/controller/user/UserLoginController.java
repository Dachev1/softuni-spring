package com.paintingscollectors.controller.user;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.UserLoginDTO;
import com.paintingscollectors.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private final UserService userService;
    private final UserSession userSession;

    public UserLoginController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
    }

    @ModelAttribute("userData")
    public UserLoginDTO initUserData() {
        return new UserLoginDTO();
    }

    @GetMapping("/login")
    public String showLoginPage() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("userData") UserLoginDTO userData,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessages", bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList());
            return "login";
        }

        if (!userService.authenticate(userData)) {
            model.addAttribute("errorMessages", List.of("Incorrect username or password!"));
            return "login";
        }

        return "redirect:/home";
    }

    //@PostMapping is the right one, but for the task  I will use @GetMapping
    @GetMapping("/logout")
    public String logout() {
        userSession.clear();
        return "redirect:/users/login";
    }
}
