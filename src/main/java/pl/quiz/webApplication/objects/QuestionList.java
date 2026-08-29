package pl.quiz.webApplication.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionList {

    private List<Question> questions = new ArrayList<>();

}