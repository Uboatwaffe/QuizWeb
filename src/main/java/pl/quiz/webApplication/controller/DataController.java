package pl.quiz.webApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.Question;
import pl.quiz.webApplication.objects.Set;

import java.util.List;


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

    // TODO: make this an MVC app (delete rest controller)


    /**
     * This method returns all sets belonging to a current user
     * @param authentication authentication object
     * @return List of Sets available
     */
    @GetMapping("/chooseSet")
    public List<Set> chooseSets(Authentication authentication) {
        return dataRepository.getAllSets(authentication.getName());
    }



    /**
     * This method updates old questions
     * @param listOfOldQuestions list of updated questions
     * @return ResponseEntity
     */
    @PutMapping("/updateQuestions")
    public ResponseEntity<?> updateQuestions(@RequestBody List<Question> listOfOldQuestions) {

        try {
            for (Question question : listOfOldQuestions) {
                if (!dataRepository.updateQuestion(question)){
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}












