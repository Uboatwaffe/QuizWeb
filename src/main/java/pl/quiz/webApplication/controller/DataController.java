package pl.quiz.webApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.quiz.webApplication.data.DataRepository;




/**
 * This class is responsible for sending data to user
 */
@RestController
@RequestMapping("/api")
public class DataController {

    /**
     * This filed is automatically set up by spring
     */
    @Autowired
    DataRepository dataRepository;


    @PostMapping("/login/login")
    public int loginUser(@RequestBody String body){
        System.out.println(body);
        return 200;
    }
}