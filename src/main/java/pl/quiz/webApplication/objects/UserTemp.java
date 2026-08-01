package pl.quiz.webApplication.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import pl.quiz.webApplication.Role;

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
    @Id
    private Object id;

    @Field("username")
    private String login;

    @Field("password")
    private String password;

    @Field("role")
    private Role role;
}
