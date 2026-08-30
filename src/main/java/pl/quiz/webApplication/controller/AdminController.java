package pl.quiz.webApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.User;
import pl.quiz.webApplication.submissons.UserSubmission;

import java.util.List;

/**
 * This class is a controller for every admin endpoint
 */
@Controller
@RequiredArgsConstructor
public class AdminController {

    /**
     * This is autoinjected data repository field
     */
    private final DataRepository dataRepository;

    /**
     * This method displays every user that can be deleted except users own
     *
     * @param model          model for Thymeleaf
     * @param authentication authentication object
     * @return delete_user.html
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete_user")
    public String deleteUser(
            Model model,
            Authentication authentication) {

        List<User> allUsers =
                dataRepository.getAllUsers();

        allUsers.removeIf(user ->
                user.getLogin().equals(authentication.getName())
        );

        model.addAttribute(
                "users",
                allUsers
        );

        return "delete_user";
    }

    /**
     * This method deletes specified user
     * @param id id of the user to be deleted
     * @return reloads the page (delete_user.html)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete_user/{id}")
    public String deleteUser(
            @PathVariable("id") String id) {

        dataRepository.deleteUser(id);

        return "redirect:/delete_user";
    }

    /**
     * This method displays every set in the database
     * @param model model for Thymeleaf
     * @return delete_any_set.html
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete_any_set")
    public String deleteAnySet(Model model) {

        model.addAttribute(
                "sets",
                dataRepository.getEverySet()
        );

        return "delete_any_set";
    }

    /**
     * This method deletes specified set
     *
     * @param name name of the set to be deleted
     * @return reloads the page (delete_any_set.html)
     */
    @PreAuthorize("hasRole('ADMIN')")
    // TODO: dont delete set of different user of the same name
    @PostMapping("/delete_any_set/{name}")
    public String deleteAnySet(
            @PathVariable("name") String name) {

        dataRepository.deleteSetNoAuth(name);

        return "redirect:/delete_any_set";
    }

    /**
     * This method displays every user in the database
     * @param model model for Thymeleaf
     * @return change_role.html
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/change_role")
    public String changeRole(Model model) {

        UserSubmission submission =
                new UserSubmission();

        submission.setUsers(
                dataRepository.getAllUsers()
        );

        model.addAttribute(
                "userSubmission",
                submission
        );

        return "change_role";
    }

    /**
     * This method changes the roles and/or username of users
     * @param submission list of users to be updated
     * @param model model for Thymeleaf
     * @return reloads if something went wrong, if not then home.html
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/change_role")
    public String changeRole(
            @ModelAttribute("userSubmission")
            UserSubmission submission,
            Model model) {

        for (User user : submission.getUsers()) {

            if (!dataRepository.updateUser(user)) {

                model.addAttribute(
                        "error",
                        "Could not update users"
                );

                submission.setUsers(
                        dataRepository.getAllUsers()
                );

                model.addAttribute(
                        "userSubmission",
                        submission
                );

                return "change_role";
            }
        }

        //TODO: forbid the admin from changing himself

        return "redirect:/home";
    }
}