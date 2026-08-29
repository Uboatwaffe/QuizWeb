package pl.quiz.webApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.objects.Question;
import pl.quiz.webApplication.objects.SessionUser;
import pl.quiz.webApplication.objects.Set;
import pl.quiz.webApplication.objects.User;

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


    /**
     * This method updates users
     * @param users List of user details
     * @param session current session
     * @return ResponseEntity
     */
    // TODO: Delete session
    @PutMapping("/updateUsers")
    public ResponseEntity<?> updateUsers(@RequestBody List<User> users, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        if (!sessionUser.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        for (User user : users) {
            if (!dataRepository.updateUser(user)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        return ResponseEntity.ok().build();
    }
}












