package pl.quiz.webApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.SessionUser;
import pl.quiz.webApplication.objects.Set;

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

    /**
     * Returns HTML page
     * @return choose_set_to_start.html
     */
    @GetMapping("/choose_set_to_start")
    public String chooseSetToStart(){
        return "choose_set_to_start";
    }

    /**
     * Returns HTML page
     * @return choose_set_to_modify.html
     */
    @GetMapping("/choose_set_to_modify")
    public String chooseSetToModify(){
        return "choose_set_to_modify";
    }

    /**
     * Returns HTML page
     * @param name name of the quiz to be run
     * @param model model for ThymeLeaf
     * @return quiz.html
     */
    @GetMapping("/quiz/{name}")
    public String quiz(@PathVariable("name") String name, Model model, HttpSession session){
        model.addAttribute("set", new Set(name));
        return "quiz";
    }

    /**
     * Returns HTML page
     * @param name name of the set to be modified
     * @param model model for ThymeLeaf
     * @param session current session
     * @return modify_set.html
     */
    @GetMapping("/modify/{name}")
    public String modify(@PathVariable("name") String name, Model model, HttpSession session){
        model.addAttribute("set", new Set(name));
        return "modify_set";
    }

    /**
     * Returns HTML page
     * @param model model for ThymeLeaf
     * @param session current session
     * @return score.html
     */
    @GetMapping("/score")
    public String score(Model model, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        model.addAttribute("user", sessionUser);
        return "score";
    }
}