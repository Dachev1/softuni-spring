package bg.softuni.mobilele.web.user;

import bg.softuni.mobilele.model.dtos.user.LoginDTO;
import bg.softuni.mobilele.model.entity.User;
import bg.softuni.mobilele.service.UserService;
import jakarta.servlet.http.HttpSession;
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
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDTO", new LoginDTO("", ""));
        return "auth-login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginDTO loginDTO,
            BindingResult bindingResult,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "auth-login";
        }

        User user = userService.validateAndGetUser(loginDTO);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }

        bindingResult.rejectValue("password", "invalid.credentials", "Invalid username or password");
        return "auth-login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
