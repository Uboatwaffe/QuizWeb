package pl.quiz.webApplication.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import pl.quiz.webApplication.enums.Role;

/**
 * This class represents a user
 * <p>Created on 27.07.2026</p>
 * @author Maciej
 * @version 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Field("username")
    private String login;

    @Field("password")
    private String password;

    @Field("role")
    private Role role;

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
