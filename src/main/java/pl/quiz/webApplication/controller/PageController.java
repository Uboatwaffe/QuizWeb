package pl.quiz.webApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.quiz.webApplication.objects.SessionUser;

/**
 * This class is responsible for loading correct pages <br>
 * Uses Thymeleaf
 *
 */
@Controller
public class PageController {

    /**
     * Returns HTML page
     * @return index.html
     */
    @GetMapping("/")
    public String index(Model model) {
        SessionUser sessionUser = new SessionUser();
        model.addAttribute("user", sessionUser);

        return "index";
    }


    /**
     * Returns HTML page
     * @param session current session
     * @param model model for ThymeLeaf
     * @return home.html
     */
    @GetMapping("/home")
    public String home(HttpSession session, Model model){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        model.addAttribute("user", sessionUser);

        return "home";
    }

    /**
     * Returns HTML page
     * @return signup.html
     */
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    /**
     * Returns HTML page
     * @param session current session
     * @param model model for ThymeLeaf
     * @return new_set.html
     */
    @GetMapping("/new_set")
    public String newSet(HttpSession session, Model model){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        model.addAttribute("user", sessionUser);

        return "new_set";
    }

    /**
     * Returns HTML page
     * @param session current session
     * @return choose_set.html
     */
    @GetMapping("/choose_set")
    public String chooseSet(HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        return "choose_set";
    }

}