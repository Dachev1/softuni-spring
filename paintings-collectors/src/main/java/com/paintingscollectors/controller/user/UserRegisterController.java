package com.paintingscollectors.controller.user;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.UserRegisterDTO;
import com.paintingscollectors.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserRegisterController {

    private final UserService userService;
    private final UserSession userSession;

    public UserRegisterController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
    }

    @ModelAttribute("userData")
    public UserRegisterDTO initUserData() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("userData") UserRegisterDTO userData,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userData", userData);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userData", bindingResult);
            return "redirect:/users/register";
        }

        if (!userData.getPassword().equals(userData.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userData", userData);
            redirectAttributes.addFlashAttribute("passwordMismatch", "Passwords do not match!");
            return "redirect:/users/register";
        }

        userService.register(userData);
        return "redirect:/users/login";
    }
}
