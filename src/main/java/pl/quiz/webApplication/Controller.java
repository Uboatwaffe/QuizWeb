package pl.quiz.webApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Created on 21.07.2026</p>
 *
 * @author Maciej
 * @version 0.1
 */
@RestController
public class Controller {

    @Autowired
    DataRepository dataRepository;

    @GetMapping("")
    public List<Test> test(){
        return dataRepository.test();
    }
}
