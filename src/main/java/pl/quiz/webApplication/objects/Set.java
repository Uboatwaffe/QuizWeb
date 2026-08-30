package pl.quiz.webApplication.objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * This class contains the name of current set
 * <p>Created on 02.08.2026</p>
 * @author Maciej
 * @version 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Set {

    /**
     * Set name
     */
    @Field("set")
    @NotBlank(message = "Set name cannot be blank")
    @Size(max = 20, message = "Set name must be 20 characters or shorter")
    private String name;

    /**
     * Owner of the set
     */
    @Field("owner")
    private String owner;

    /**
     * Constructor for only name
     * @param name name of the set
     */
    public Set(String name) {
        this.name = name;
    }
}
