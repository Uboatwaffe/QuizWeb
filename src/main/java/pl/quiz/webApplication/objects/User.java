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

    /**
     * ID of user
     */
    @Id
    private String id;

    /**
     * Users login
     */
    @Field("username")
    private String login;

    /**
     * Users password
     */
    @Field("password")
    private String password;

    /**
     * Users role
     */
    @Field("role")
    private Role role;

    /**
     * Constructor without id
     * @param login users login
     * @param password users password
     * @param role users role
     */
    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
