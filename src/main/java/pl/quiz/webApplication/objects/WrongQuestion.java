package pl.quiz.webApplication.objects;

import lombok.Builder;
import lombok.Getter;

/**
 * Class that contains info of question that user got wrong during the quiz
 */
@Getter
@Builder
public class WrongQuestion {

    /**
     * Question
     */
    private String question;

    /**
     * Points
     */
    private int points;

    /**
     * Users answer
     */
    private String userAnswer;

    /**
     * Correct answer
     */
    private String correctAnswer;
}
