package pl.quiz.webApplication.submissons;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a wrapper for user answers
 */
@Setter
@Getter
public class AnswerSubmission {

    private String questionId;

    private List<String> answers = new ArrayList<>();
}