package pl.quiz.webApplication.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.quiz.webApplication.enums.Role;

/**
 * This class is so that during session password is not leaked (cut User class)
 * <p>Created on 01.08.2026</p>
 * @author Maciej
 * @version 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionUser {
    private String login;
    private Role role;
}
