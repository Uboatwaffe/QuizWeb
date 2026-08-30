package pl.quiz.webApplication.submissons;

import lombok.Getter;
import lombok.Setter;
import pl.quiz.webApplication.objects.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserSubmission {

    private List<User> users = new ArrayList<>();

}