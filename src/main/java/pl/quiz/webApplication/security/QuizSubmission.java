package pl.quiz.webApplication.security;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class QuizSubmission {

    private List<AnswerSubmission> answers = new ArrayList<>();

    @Override
    public String toString() {
        return "QuizSubmission{" +
                "answers=" + answers +
                '}';
    }
}