package pl.quiz.webApplication.objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
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
    @Field("set")
    @NotBlank(message = "Set name cannot be blank")
    @Max(value = 20, message = "Set name cannot exceed 20 characters")
    private String name;
}
