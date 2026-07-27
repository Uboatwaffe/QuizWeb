package pl.quiz.webApplication.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Created on 27.07.2026</p>
 *
 * @author Maciej
 * @version 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTemp {
    private String login;
    private String password;
}
