package pl.quiz.webApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.enums.Type;
import pl.quiz.webApplication.objects.*;

import java.util.ArrayList;
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
     * This method creates new set of questions
     *
     * @param set     object that contains details of set to be created
     * @param authentication authentication object
     * @return ResponseEntity
     */
    @PostMapping("newSet")
    public ResponseEntity<?> createNewSet(@RequestBody Set set, Authentication authentication) {

        if (set.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Check if the name is already taken
        if (dataRepository.checkIfExists("question", "set", set.getName(), "owner", authentication.getName(), Set.class)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        Question question = dataRepository.addQuestion("Are you ready?", Type.YN, "YES", 0, set.getName(), authentication.getName());

        if (question != null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

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
     * This method deletes the set of name specified in path
     *
     * @param name    name of the set to be deleted
     * @param authentication authentication object
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteSet(@PathVariable("name") String name, Authentication authentication) {

        if (dataRepository.deleteSet(name, authentication.getName())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * This method returns list of questions in specified set
     *
     * @param set     set of the questions
     * @param authentication authentication object
     * @return List of questions
     */
    @GetMapping("/quiz/{set}")
    public List<Question> getQuestions(@PathVariable("set") String set, Authentication authentication) {
        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        SessionUser sessionUser = new SessionUser(authentication.getName(), Role.valueOf(role));

        return dataRepository.getQuestions(sessionUser, set);
    }

    /**
     * This method deletes question of specified id
     *
     * @param id id of the question to be deleted
     * @return ResponseEntity
     */
    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id") String id) {
        if (dataRepository.deleteQuestion(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * This method inserts new questions into DB
     * @param listOfNewQuestions list of question details
     * @param authentication authentication object
     * @return ResponseEntity
     */
    @PostMapping("/newQuestions")
    public ResponseEntity<?> newQuestions(@RequestBody List<Question> listOfNewQuestions, Authentication authentication) {

        try {
            for (Question question : listOfNewQuestions) {
                dataRepository.addQuestion(question.getQuestion(), question.getType(), question.getAnswer()
                        , question.getPoints(), question.getSet(), authentication.getName());
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
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
     * This method checks answers sent by user and returns score
     * @param set set that the user has been solving
     * @param list list of id and answer
     * @param authentication authentication object
     * @return Score (yourScore / outOfPossiblePoints)
     */
    @PostMapping("/submitAnswers/{name}")
    public Score submitAnswers(@PathVariable("name") Set set, @RequestBody List<Question> list, Authentication authentication) {

        int scoredPoints = 0;

        for  (Question question : list) {
            scoredPoints += dataRepository.checkAnswer(question.getId(), question.getAnswer());

        }

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        SessionUser sessionUser = new SessionUser(authentication.getName(), Role.valueOf(role));


        return new Score(scoredPoints, dataRepository.allPointsInSet(set, sessionUser));

    }

    /**
     * This method returns every user in database
     *
     * @param session current session
     * @return List of users
     */
    // TODO: delete session
    @GetMapping("/getUsers")
    public List<User> getUsers(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        if (!sessionUser.getRole().equals(Role.ADMIN)) {
            return new ArrayList<>();
        }

        return dataRepository.getAllUsers();
    }

    /**
     * This method deletes user of specified id
     * @param id id of the user to be deleted
     * @param session current session
     * @return ResponseEntity
     */
    // TODO: Delete session
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        if (!sessionUser.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return dataRepository.deleteUser(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    /**
     * This method returns every set in the database
     * @param session current session
     * @return List of sets
     */
    // TODO: Delete session
    @GetMapping("/chooseAnySet")
    public List<Set> chooseAnySets(HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        if (!sessionUser.getRole().equals(Role.ADMIN)) {
            return new ArrayList<>();
        }

        return dataRepository.getEverySet();
    }

    /**
     * This method deletes set without checking if the current user is the owner of said set
     * @param name name of the set to be deleted
     * @return ResponseEntity
     */
    @DeleteMapping("/deleteNoAuth/{name}")
    public ResponseEntity<?> deleteSet(@PathVariable("name") String name) {
        if (dataRepository.deleteSetNoAuth(name)) {
            return ResponseEntity.ok().build();
        } else {
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












