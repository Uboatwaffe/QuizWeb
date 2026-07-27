package pl.quiz.webApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/test")
    public String testPage() {
        return "test";
    }
}