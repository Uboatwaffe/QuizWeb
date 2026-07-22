package pl.quiz.webApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.webApplication.Test;
import pl.quiz.webApplication.data.DataRepository;

import java.util.List;

/**
 * This class is responsible for REST API
 * <p>Created on 21.07.2026</p>
 * @author Maciej
 * @version 0.1
 */
@RestController
public class Controller {

    /**
     * This field is automatically set up by Spring Boot
     */
    @Autowired
    DataRepository dataRepository;

    /**
     * This is a test method. It shows whether data extraction from database is possible
     * @return contents of test collection
     */
    @GetMapping("")
    public List<Test> test(){
        return dataRepository.test();
    }
}
