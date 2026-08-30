package pl.quiz.webApplication.objects;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.quiz.webApplication.enums.Type;

/**
 * This class represents details of question
 * <p>Created on 02.08.2026</p>
 * @author Maciej
 * @version 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @NonNull
    private String id;
    @NotBlank(message = "Question cannot be blank")
    private String question;
    @NonNull
    private Type type;
    @NotBlank(message = "Question must have an answer")
    private String answer;
    @NotBlank(message = "This field can't be empty")
    private int points;
    @NotBlank(message = "This question must be assigned to some set")
    private String set;
    @NonNull
    private String owner;


    public Question(String question, Type type, String answer, int points, String set, String owner){
        this.question = question;
        this.type = type;
        this.answer = answer;
        this.points = points;
        this.set = set;
        this.owner = owner;
    }
}
