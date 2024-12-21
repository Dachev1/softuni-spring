package bg.softuni.pathfinder.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "index";
    }
}
