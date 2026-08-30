package pl.quiz.webApplication.submissons;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import pl.quiz.webApplication.objects.Question;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionSubmission {

    @Valid
    private List<Question> questions = new ArrayList<>();

}