package com.paintingscollectors.controller;

import com.paintingscollectors.model.dto.LoginRequest;
import com.paintingscollectors.model.dto.RegisterRequest;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("loginRequest", new LoginRequest());
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest,
                              BindingResult bindingResult,
                              HttpSession session) {
        ModelAndView mav = new ModelAndView("login");

        if (bindingResult.hasErrors()) {
            return mav;
        }

        Optional<User> userOpt = userService.login(loginRequest);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("user_id", user.getId());
            log.info("User with id {} has logged in", user.getId());
            return new ModelAndView("redirect:/home");
        } else {
            // Associate the error with the username field (or a global error, if preferred).
            bindingResult.rejectValue("username", "error.loginRequest", "Invalid username or password");
            return mav;
        }
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("registerRequest", new RegisterRequest());
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("registerRequest") RegisterRequest registerRequest,
                                 BindingResult bindingResult) {

        if (userService.usernameExists(registerRequest.getUsername())) {
            bindingResult.rejectValue("username", "error.registerRequest", "Username already exists");
        }

        if (userService.emailExists(registerRequest.getEmail())) {
            bindingResult.rejectValue("email", "error.registerRequest", "Email already exists");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.registerRequest", "Passwords do not match");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        User user = userService.register(registerRequest);
        log.info("User with id {} is registered, password encoded: {}", user.getId(), user.getPassword());
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();

        log.info("Session invalidated, user logged out");

        return new ModelAndView("redirect:/");
    }
}
