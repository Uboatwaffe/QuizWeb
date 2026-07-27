package pl.quiz.webApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.quiz.webApplication.Test;
import pl.quiz.webApplication.data.DataRepository;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    DataRepository dataRepository;


    @GetMapping("/api/test")
    public List<Test> test() {
        return dataRepository.test();
    }
}