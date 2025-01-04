package com.bonappetit.controller;

import com.bonappetit.dtos.UserLoginDTO;
import com.bonappetit.dtos.UserRegisterDTO;
import com.bonappetit.service.UserService;
import com.bonappetit.config.UserSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserSession userSession;

    public UserController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        if (!model.containsAttribute("userRegisterDTO")) {
            model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.userRegisterDTO", "Passwords do not match!");
        }
        if (bindingResult.hasErrors() || !userService.register(userRegisterDTO)) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/users/register";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        if (!model.containsAttribute("userLoginDTO")) {
            model.addAttribute("userLoginDTO", new UserLoginDTO());
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors() && !userService.login(userLoginDTO)) {
            bindingResult.reject("loginError", "Invalid username or password.");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password.");
            return "redirect:/users/login";
        }
        return "redirect:/home";
    }


    @GetMapping("/logout")
    public String logout() {
        userSession.logout();
        return "redirect:/";
    }
}
