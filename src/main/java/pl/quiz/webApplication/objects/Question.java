package pl.quiz.webApplication.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import pl.quiz.webApplication.enums.Type;


/**
 * This class is responsible for storing the details of question
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    /**
     * ID of the question
     */
    @Id
    private String id;

    /**
     * Question
     */
    private String question;

    /**
     * Type of question
     */
    private Type type;

    /**
     * Answer to the question
     */
    private String answer;

    /**
     * Points for getting the question right
     */
    private int points;

    /**
     * Set to which this question belongs
     */
    private String set;

    /**
     * Owner of this question
     */
    private String owner;

    /**
     * Constructor
     * @param question question
     * @param type type of question
     * @param answer answer to the question
     * @param points points for getting the question right
     * @param set set to which this question belongs
     * @param owner owner of this question
     */
    public Question(String question, Type type, String answer, int points, String set, String owner) {
        this.question = question;
        this.type = type;
        this.answer = answer;
        this.points = points;
        this.set = set;
        this.owner = owner;
    }
}