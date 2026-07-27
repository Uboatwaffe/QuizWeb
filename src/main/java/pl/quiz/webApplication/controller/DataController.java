package pl.quiz.webApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.UserTemp;


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

    /**
     * This method receives user details
     * @param userTemp details of user trying to log in
     * @return 200
     */
    @PostMapping("/login/login")
    public int loginUser(@RequestBody UserTemp userTemp){
        System.out.println(userTemp.getLogin() + "\n" + userTemp.getPassword());
        return 200;
    }
}