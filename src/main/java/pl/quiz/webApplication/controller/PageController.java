package pl.quiz.webApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.UserTemp;

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

        UserTemp user = (UserTemp) session.getAttribute("user");

        model.addAttribute("user", user);

        return "home";
    }
}