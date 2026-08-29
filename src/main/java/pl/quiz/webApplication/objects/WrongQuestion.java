package pl.quiz.webApplication.objects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WrongQuestion {

    private String question;
    private int points;
    private String userAnswer;
    private String correctAnswer;
}
