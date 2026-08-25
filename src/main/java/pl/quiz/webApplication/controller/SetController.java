package pl.quiz.webApplication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Type;
import pl.quiz.webApplication.objects.Question;
import pl.quiz.webApplication.objects.Set;

@Controller
@RequiredArgsConstructor
public class SetController {

    private final DataRepository dataRepository;

    /**
     * Returns HTML page
     *
     * @return new_set.html
     */
    @GetMapping("/new_set")
    public String newSet(Model model) {
        model.addAttribute("set", new Set());
        return "new_set";
    }

    /**
     * Returns HTML page
     *
     * @return choose_set_to_delete.html
     */
    @GetMapping("/choose_set_to_delete")
    public String chooseSetToDelete(Model model, Authentication authentication) {

        model.addAttribute("sets", dataRepository.getAllSets(authentication.getName()));

        return "choose_set_to_delete";
    }


    /**
     * This method creates new set
     *
     * @param set            details of the set to be created
     * @param result         result of validation check
     * @param authentication authentication object
     * @param model          model for Thymeleaf
     * @return updated view if any errors, if not then home.html
     */
    @PostMapping("/new_set")
    public String createNewSet(
            @ModelAttribute("set") @Valid
            Set set,
            BindingResult result,
            Authentication authentication,
            Model model) {


        if (result.hasErrors()) {
            return "new_set";
        }

        if (dataRepository.checkIfExists(
                "question",
                "set",
                set.getName(),
                "owner",
                authentication.getName(),
                Set.class)) {

            result.rejectValue(
                    "name",
                    "duplicate",
                    "A set with this name already exists"
            );

            return "new_set";
        }

        Question question = dataRepository.addQuestion(
                "Are you ready?",
                Type.YN,
                "YES",
                0,
                set.getName(),
                authentication.getName()
        );

        if (question != null) {
            return "redirect:/home";
        }

        return "new_set";
    }

    /**
     * This method deletes the specified set
     * @param name name of the set to be deleted
     * @param authentication authentication object
     * @param redirectAttributes object to pass the model for the next view
     * @return updated view of choose_set_to_delete.html
     */
    @PostMapping("/delete/{name}")
    public String deleteSet(
            @PathVariable("name") String name,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        if (dataRepository.deleteSet(name, authentication.getName())) {
            return "redirect:/choose_set_to_delete";
        }

        redirectAttributes.addFlashAttribute(
                "error",
                "The set could not be deleted."
        );

        return "redirect:/choose_set_to_delete";
    }

}
