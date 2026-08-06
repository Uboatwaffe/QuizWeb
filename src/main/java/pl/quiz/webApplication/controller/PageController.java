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
    public String index() {
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
     * @return new_set.html
     */
    @GetMapping("/new_set")
    public String newSet(){
        return "new_set";
    }

    /**
     * Returns HTML page
     * @return choose_set_to_delete.html
     */
    @GetMapping("/choose_set_to_delete")
    public String chooseSetToDelete(){
        return "choose_set_to_delete";
    }

    @GetMapping("/choose_set_to_start")
    public String chooseSetToStart(){
        return "choose_set_to_start";
    }
}