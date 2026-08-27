package pl.quiz.webApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.Set;

/**
 * This class is responsible for loading correct pages <br>
 * Uses Thymeleaf
 *
 */
@SuppressWarnings("SameReturnValue")
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
    public String quiz(@PathVariable("name") String name, Model model) {
        model.addAttribute("set", new Set(name));
        return "quiz";
    }

    /**
     * Returns HTML page
     * @param name name of the set to be modified
     * @param model model for ThymeLeaf
     * @return modify_set.html
     */
    @GetMapping("/modify/{name}")
    public String modify(@PathVariable("name") String name, Model model) {
        model.addAttribute("set", new Set(name));
        return "modify_set";
    }

    /**
     * Returns HTML page
     * @param model model for ThymeLeaf
     * @param authentication authentication object
     * @return score.html
     */
    @GetMapping("/score")
    public String score(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "score";
    }

    @GetMapping("/delete_user")
    public String deleteUser() {
        return "delete_user";
    }

    @GetMapping("/delete_any_set")
    public String deleteAnySet() {
        return "delete_any_set";
    }

    @GetMapping("change_role")
    public String changeRole() {
        return "change_role";
    }
}