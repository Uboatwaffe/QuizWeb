package pl.quiz.webApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.objects.User;

@Controller
public class SignupController {

    private final DataRepository dataRepository;

    public SignupController(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String passwordRepeat,
            @RequestParam String role) {

        if (!password.equals(passwordRepeat)) {
            return "redirect:/signup?error=password";
        }

        Role userRole = Role.valueOf(role);

        User user = dataRepository.addUser(
                login,
                password,
                userRole
        );

        if (user == null) {
            return "redirect:/signup?error=exists";
        }

        return "redirect:/?signupSuccess=true";
    }
}