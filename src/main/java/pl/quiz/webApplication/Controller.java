package pl.quiz.webApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Created on 21.07.2026</p>
 *
 * @author Maciej
 * @version 0.1
 */
@RestController
public class Controller {

    @GetMapping("")
    public String test(){
        return "test";
    }
}
