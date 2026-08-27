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
import pl.quiz.webApplication.objects.Set;
import pl.quiz.webApplication.security.AnswerSubmission;
import pl.quiz.webApplication.security.QuizSubmission;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final DataRepository dataRepository;

    /**
     * Returns HTML page
     *
     * @param name  name of the quiz to be run
     * @param model model for ThymeLeaf
     * @return quiz.html
     */
    @GetMapping("/quiz/{name}")
    public String quiz(@PathVariable("name") String name, Model model, Authentication authentication) {

        model.addAttribute("set", new Set(name));
        model.addAttribute("questions", dataRepository.getQuestions(authentication.getName(), name));

        return "quiz";
    }

    @PostMapping("/submit_answers")
    public String submitAnswer(
            @ModelAttribute QuizSubmission submission,
            Model model,
            Authentication authentication) {

        System.out.println("SUBMISSION:");
        System.out.println(submission);

        for (AnswerSubmission answer : submission.getAnswers()) {
            System.out.println(
                    "Question ID: " + answer.getQuestionId()
                            + ", Answers: " + answer.getAnswers()
            );
        }

        return "score";
    }
}
