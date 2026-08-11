package pl.quiz.webApplication.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is a score object it has fields correlating to number of points collected by user and max number of points
 * <p>Created on 10.08.2026</p>
 * @author Maciej
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private int scored;
    private int outOf;
}
