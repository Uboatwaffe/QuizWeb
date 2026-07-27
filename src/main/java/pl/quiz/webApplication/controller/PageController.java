package pl.quiz.webApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String home() {
        return "index";
    }

    /**
     * @return login.html
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}