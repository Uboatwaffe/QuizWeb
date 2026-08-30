package pl.quiz.webApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.objects.User;

/**
 * This class is a controller that is responsible for handling signup logic
 */
@Controller
@RequiredArgsConstructor
public class SignupController {

    /**
     * This field is autoinjected by Spring
     */
    private final DataRepository dataRepository;

    /**
     * This method return signup page
     *
     * @return signup.html
     */
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    /**
     * This method signs up new user after validating data
     * @param login login of the new user
     * @param password password of the user
     * @param passwordRepeat repeated password of the user
     * @param role role which the user wants to have
     * @return reloads if any error has occurred, if not then home.html
     */
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