package pl.quiz.webApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.quiz.webApplication.data.DataRepository;

/**
 * This class is responsible for loading correct pages <br>
 * Uses Thymeleaf
 *
 */
@Controller
public class PageController {

    @Autowired
    DataRepository dataRepository;

    /**
     * Returns HTML page
     * @return index.html
     */
    @GetMapping("/login")
    public String index() {
        return "index";
    }


    /**
     * Returns HTML page
     * @return home.html
     */
    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {

        model.addAttribute("username", authentication.getName());

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        model.addAttribute("role", role);

        return "home";
    }
}