package pl.quiz.webApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.webApplication.Test;
import pl.quiz.webApplication.data.DataRepository;

import java.util.List;

/**
 * This class is responsible for sending data to user
 */
@RestController
public class DataController {

    /**
     * This filed is automatically set up by spring
     */
    @Autowired
    DataRepository dataRepository;

    /**
     * This method returns test data
     * @return test data
     */
    @GetMapping("/api/test")
    public List<Test> test() {
        return dataRepository.test();
    }
}