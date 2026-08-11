package pl.quiz.webApplication.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.webApplication.enums.Role;

/**
 * This class is to represent details of new user signing in
 * <p>Created on 01.08.2026</p>
 * @author Maciej
 * @version 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
    private String login;
    private String passwordOne;
    private String passwordRepeat;
    private Role role;
}
