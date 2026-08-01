package pl.quiz.webApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.quiz.webApplication.objects.SessionUser;
import pl.quiz.webApplication.objects.User;

/**
 * This class is responsible for loading correct pages <br>
 * Uses Thymeleaf
 *
 */
@Controller
public class PageController {

    /**
     * @return index.html
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * @return login.html
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model){

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        model.addAttribute("user", sessionUser);

        return "home";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
}