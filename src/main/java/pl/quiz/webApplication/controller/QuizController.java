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
import pl.quiz.webApplication.objects.Question;
import pl.quiz.webApplication.objects.Set;
import pl.quiz.webApplication.objects.WrongQuestion;
import pl.quiz.webApplication.submissons.AnswerSubmission;
import pl.quiz.webApplication.submissons.QuizSubmission;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a controller responsible for handling quiz logic
 */
@Controller
@RequiredArgsConstructor
public class QuizController {

    /**
     * This field is automatically injected by Spring
     */
    private final DataRepository dataRepository;

    /**
     * Returns HTML page
     * @param authentication authentication object
     * @param name  name of the quiz to be run
     * @param model model for ThymeLeaf
     * @return quiz.html
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/quiz/{name}")
    public String quiz(@PathVariable("name") String name, Model model, Authentication authentication) {

        model.addAttribute("set", new Set(name));
        model.addAttribute("questions", dataRepository.getQuestions(authentication.getName(), name));

        return "quiz";
    }

    /**
     * This method counts correct answers and remembers the ones which the user got wrong
     *
     * @param submission     quiz submission
     * @param model          model for Thymeleaf
     * @param authentication authentication object
     * @return home.html if something went wrong, if not score.html
     */
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/submit_answers")
    public String submitAnswer(
            @ModelAttribute QuizSubmission submission,
            Model model,
            Authentication authentication) {

        int score = 0;
        Set set = null;
        boolean correct = true;

        List<WrongQuestion> wrongQuestions = new ArrayList<>();

        for (AnswerSubmission answer : submission.getAnswers()) {

            set = dataRepository.getSetFromId(answer.getQuestionId());

            List<String> userAnswers =
                    new ArrayList<>(answer.getAnswers());

            Question correctQuestion =
                    dataRepository.getQuestionById(
                            answer.getQuestionId()
                    );

            // DB: "A,C"
            List<String> correctAnswers =
                    new ArrayList<>(
                            List.of(correctQuestion.getAnswer().split(","))
                    );

            // Remove whitespace
            userAnswers.replaceAll(String::trim);
            correctAnswers.replaceAll(String::trim);

            // Order doesn't matter
            userAnswers.sort(String::compareTo);
            correctAnswers.sort(String::compareTo);

            if (userAnswers.equals(correctAnswers)) {
                score += correctQuestion.getPoints();
            } else {
                correct = false;


                if (userAnswers.isEmpty()) {
                    wrongQuestions.add(WrongQuestion.builder()
                            .question(correctQuestion.getQuestion())
                            .points(correctQuestion.getPoints())
                            .userAnswer(" nothing")
                            .correctAnswer("'" + correctQuestion.getAnswer() + "'")
                            .build());
                } else {
                    wrongQuestions.add(WrongQuestion.builder()
                            .question(correctQuestion.getQuestion())
                            .points(correctQuestion.getPoints())
                            .userAnswer(": " + userAnswers.toString()
                                    .replace("[", "'").replace("]", "'"))
                            .correctAnswer("'" + correctQuestion.getAnswer() + "'")
                            .build());
                }
            }
        }

        if (set == null) {
            return "redirect:/home";
        }

        if (!correct) {
            model.addAttribute("mistakes", true);
        }

        model.addAttribute("questions", wrongQuestions);

        model.addAttribute("username", authentication.getName());
        model.addAttribute("score", score);

        model.addAttribute(
                "maxPoints",
                dataRepository.allPointsInSet(
                        set,
                        authentication.getName()
                )
        );

        return "score";
    }

    /**
     * Returns HTML page
     *
     * @param model          model for ThymeLeaf
     * @param authentication authentication object
     * @return score.html
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/score")
    public String score(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "score";
    }
}
