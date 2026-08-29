package pl.quiz.webApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.quiz.webApplication.data.DataRepository;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final DataRepository dataRepository;

    @GetMapping("/delete_user")
    public String deleteUser(Model model) {

        model.addAttribute(
                "users",
                dataRepository.getAllUsers()
        );

        return "delete_user";
    }

    @PostMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable("id") String id) {

        dataRepository.deleteUser(id);

        return "redirect:/delete_user";
    }

    @GetMapping("/delete_any_set")
    public String deleteAnySet() {
        return "delete_any_set";
    }

    @GetMapping("/change_role")
    public String changeRole() {
        return "change_role";
    }
}