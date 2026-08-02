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
    public String index(Model model) {
        SessionUser sessionUser = new SessionUser();
        model.addAttribute("user", sessionUser);

        return "index";
    }


    /**
     * @param session current session
     * @param model current model
     * @return home.html
     */
    @GetMapping("/home")
    public String home(HttpSession session, Model model){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        model.addAttribute("user", sessionUser);

        return "home";
    }

    /**
     * @return signup.html
     */
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    /**
     * @return new_set.html
     */
    @GetMapping("/new_set")
    public String newSet(HttpSession session, Model model){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        model.addAttribute("user", sessionUser);

        return "new_set";
    }

    @GetMapping("/choose_set")
    public String chooseSet(HttpSession session, Model model){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        model.addAttribute("user", sessionUser);

        return "choose_set";
    }

}