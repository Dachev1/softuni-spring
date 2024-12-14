package bg.softuni.mobilele.web.user;

import bg.softuni.mobilele.model.dtos.user.RegistrationDTO;
import bg.softuni.mobilele.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDTO", new RegistrationDTO("", "", "", ""));
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegistrationDTO registrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth-register";
        }
        
        userService.registerUser(registrationDTO);
        return "redirect:/users/login";
    }
}