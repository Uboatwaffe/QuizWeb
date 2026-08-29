package pl.quiz.webApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.User;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final DataRepository dataRepository;

    @GetMapping("/delete_user")
    public String deleteUser(Model model, Authentication authentication) {

        List<User> allUsers = dataRepository.getAllUsers();

        allUsers.removeIf(user ->
                user.getLogin().equals(authentication.getName())
        );

        model.addAttribute("users", allUsers);

        return "delete_user";
    }

    @PostMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable("id") String id) {

        dataRepository.deleteUser(id);

        return "redirect:/delete_user";
    }

    @GetMapping("/delete_any_set")
    public String deleteAnySet(Model model) {

        model.addAttribute(
                "sets",
                dataRepository.getEverySet()
        );

        return "delete_any_set";
    }

    @PostMapping("/delete_any_set/{name}")
    public String deleteAnySet(@PathVariable("name") String name) {

        dataRepository.deleteSetNoAuth(name);

        return "redirect:/delete_any_set";
    }

    @GetMapping("/change_role")
    public String changeRole() {
        return "change_role";
    }
}