package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.dtos.user.UserRegisterDTO;
import bg.softuni.pathfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            RedirectAttributes redirectAttributes,
            Model model) {

        // Check if username already exists
        if (this.userService.usernameExists(userRegisterDTO.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "The username is already taken. Please choose another one.");
        }

        // Check if email already exists
        if (this.userService.emailExists(userRegisterDTO.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "The email is already in use. Please use a different email.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userRegisterDTO);
            return "register";
        }

        this.userService.registerUser(userRegisterDTO);

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
