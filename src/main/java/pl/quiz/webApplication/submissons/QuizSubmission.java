package pl.quiz.webApplication.submissons;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a wrapper for AnswerSubmission
 */
@Setter
@Getter
public class QuizSubmission {

    private List<AnswerSubmission> answers = new ArrayList<>();

}