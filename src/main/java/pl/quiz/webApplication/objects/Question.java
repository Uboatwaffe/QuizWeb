package pl.quiz.webApplication.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.webApplication.enums.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private String id;
    private String question;
    private Type type;
    private String answer;
    private int points;
    private String set;
    private String owner;

    public Question(String question, Type type, String answer, int points, String set, String owner) {
        this.question = question;
        this.type = type;
        this.answer = answer;
        this.points = points;
        this.set = set;
        this.owner = owner;
    }
}