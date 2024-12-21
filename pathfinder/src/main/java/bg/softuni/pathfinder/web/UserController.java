package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.dtos.user.UserRegisterDTO;
import bg.softuni.pathfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserRegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            return "redirect:/users/register";
        }

        userService.registerUser(userRegisterDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "profile";
    }
}
