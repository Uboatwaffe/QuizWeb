package pl.quiz.webApplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Type;
import pl.quiz.webApplication.objects.Question;
import pl.quiz.webApplication.objects.Set;
import pl.quiz.webApplication.security.QuestionSubmission;

@Controller
@RequiredArgsConstructor
public class SetController {

    private final DataRepository dataRepository;

    @GetMapping("/new_set")
    public String newSet(Model model) {
        model.addAttribute("set", new Set());
        return "new_set";
    }

    @PostMapping("/new_set")
    public String createNewSet(
            @ModelAttribute("set") Set set,
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

    @PostMapping("/modify/{name}")
    public String updateSet(
            @PathVariable("name") String name,
            @ModelAttribute QuestionSubmission submission,
            Authentication authentication) {

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

        return "redirect:/modify/" + name;
    }
}