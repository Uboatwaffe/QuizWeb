package pl.quiz.webApplication.objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.webApplication.enums.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private String id;

    @NotBlank(message = "Question cannot be blank")
    private String question;

    @NotNull(message = "Question type is required")
    private Type type;

    @NotBlank(message = "Question must have an answer")
    private String answer;

    @PositiveOrZero(message = "Points cannot be negative")
    private int points;

    @NotBlank(message = "This question must be assigned to some set")
    private String set;

    @NotBlank(message = "Owner is required")
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