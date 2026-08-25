package pl.quiz.webApplication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Type;
import pl.quiz.webApplication.objects.Question;
import pl.quiz.webApplication.objects.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/set")
public class SetController {

    private final DataRepository dataRepository;


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

}
