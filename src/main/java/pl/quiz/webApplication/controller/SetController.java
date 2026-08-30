package pl.quiz.webApplication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Type;
import pl.quiz.webApplication.objects.Question;
import pl.quiz.webApplication.objects.Set;
import pl.quiz.webApplication.submissons.QuestionSubmission;

/**
 * This class is a controller responsible for handling any actions linked to set management
 */
@Controller
@RequiredArgsConstructor
public class SetController {

    /**
     * This filed is autoinjected by Spring
     */
    private final DataRepository dataRepository;

    /**
     * This method returns the view to create new set
     *
     * @param model model for Thymeleaf
     * @return new_set.html
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/new_set")
    public String newSet(Model model) {
        model.addAttribute("set", new Set());
        return "new_set";
    }

    /**
     * This method validates whether new set name abides by rules if yes then its created
     * @param set details of new set
     * @param result result of validation
     * @param authentication authentication object
     * @param model model for Thymeleaf to display any errors
     * @return reloads if something went wrong, if not then home.html
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/new_set")
    public String createNewSet(
            @Valid @ModelAttribute("set") Set set,
            BindingResult result,
            Authentication authentication,
            Model model) {

        if (dataRepository.checkIfExists(
                "question",
                "set",
                set.getName(),
                "owner",
                authentication.getName(),
                Set.class)) {

            model.addAttribute("error",
                    "A set with this name already exists");

            return "new_set";
        }

        if (result.hasErrors()) {
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
     * This method displays every available set that can be deleted
     * @param model model for Thymeleaf
     * @param authentication authentication object
     * @return choose_set_to_delete.html
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/choose_set_to_delete")
    public String chooseSetToDelete(
            Model model,
            Authentication authentication) {

        model.addAttribute(
                "sets",
                dataRepository.getAllSets(authentication.getName())
        );

        return "choose_set_to_delete";
    }

    /**
     * This method deletes specified set
     * @param name name of the set to be deleted
     * @param authentication authentication object
     * @return reloads the page
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/delete/{name}")
    public String deleteSet(
            @PathVariable("name") String name,
            Authentication authentication) {

        dataRepository.deleteSet(
                name,
                authentication.getName()
        );

        return "redirect:/choose_set_to_delete";
    }

    /**
     * This method shows user which sets he can start
     * @param model model for Thymeleaf
     * @param authentication authentication object
     * @return choose_set_to_start.html
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/choose_set_to_start")
    public String chooseSetToStart(
            Model model,
            Authentication authentication) {

        model.addAttribute(
                "sets",
                dataRepository.getAllSets(authentication.getName())
        );

        return "choose_set_to_start";
    }

    /**
     * This method displays which sets user can modify
     * @param model model for Thymeleaf
     * @param authentication authentication object
     * @return choose_set_to_modify.html
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/choose_set_to_modify")
    public String chooseSetToModify(
            Model model,
            Authentication authentication) {

        model.addAttribute(
                "sets",
                dataRepository.getAllSets(authentication.getName())
        );

        return "choose_set_to_modify";
    }

    /**
     * This method displays the details of set that will be modified
     * @param name name of the set to be updated
     * @param model model for Thymeleaf
     * @param authentication authentication object
     * @return modify_set.html
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/modify/{name}")
    public String modify(
            @PathVariable("name") String name,
            Model model,
            Authentication authentication) {

        model.addAttribute("set", new Set(name));

        model.addAttribute(
                "questions",
                dataRepository.getQuestions(
                        authentication.getName(),
                        name
                )
        );

        return "modify_set";
    }

    /**
     * This method updates the set
     * @param name name of the set to be updated
     * @param submission object that contains changes
     * @param authentication authentication object
     * @return reloads the page
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/modify/{name}")
    public String updateSet(
            @PathVariable("name") String name,
            @ModelAttribute QuestionSubmission submission,
            Authentication authentication,
            Model model) {


        for (Question question : submission.getQuestions()) {

            /*
             * Existing question
             */
            if (question.getId() != null &&
                    !question.getId().isBlank()) {

                dataRepository.updateQuestion(question);

            }

            /*
             * New question
             */
            else {

                dataRepository.addQuestion(
                        question.getQuestion(),
                        question.getType(),
                        question.getAnswer(),
                        question.getPoints(),
                        name,
                        authentication.getName()
                );
            }
        }

        return "redirect:/home";
    }

    /**
     * This method deletes a question
     *
     * @param id             id of the question to be deleted
     * @param authentication authentication object
     * @return response indicating whether deletion was successful
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/delete_question/{id}")
    @ResponseBody
    public boolean deleteQuestion(
            @PathVariable("id") String id,
            Authentication authentication) {

        String name = dataRepository.getSetFromId(id).getName();

        return dataRepository.deleteQuestion(id, authentication.getName());
    }
}