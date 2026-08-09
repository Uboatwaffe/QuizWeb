package pl.quiz.webApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Answer;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.enums.Type;
import pl.quiz.webApplication.objects.*;

import java.util.List;
import java.util.Map;


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
     * @return ResponseEntity
     */
    @PostMapping("/")
    public ResponseEntity<?> loginUser(@RequestBody User userTemp, HttpSession session){
        User user = dataRepository.authenticateUser(userTemp.getLogin(), userTemp.getPassword());

        if (user != null) {

            SessionUser sessionUser = new SessionUser(user.getLogin(), user.getRole());

            session.setAttribute("user", sessionUser);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * This method handles the signing up the user and sets up the current session
     * @param newUser details of new user
     * @param session current session
     * @return ResponseEntity
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody NewUser newUser, HttpSession session){

        String login = newUser.getLogin();
        String passwordOne = newUser.getPasswordOne();
        String passwordRepeat = newUser.getPasswordRepeat();
        Role role = newUser.getRole();

        if (passwordOne.compareTo(passwordRepeat) != 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        User user = dataRepository.addUser(login, passwordOne, role);

        if (user != null){
            SessionUser sessionUser = new SessionUser(login, role);
            session.setAttribute("user", sessionUser);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }

    /**
     * This method creates new set of questions
     * @param set object that contains details of set to be created
     * @param session current session
     * @return ResponseEntity
     */
    @PostMapping("new_set")
    public ResponseEntity<?> createNewSet(@RequestBody Set set, HttpSession session) {

        if (set.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        // Check if the name is already taken
        if (dataRepository.checkIfExists("question", "set", set.getName(), "owner", sessionUser.getLogin(), Set.class)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        SessionUser user = (SessionUser) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Question question = dataRepository.addQuestion(true, "Are you ready?", Type.YN, Answer.YES, 0, set.getName(), user.getLogin());

        if (question != null) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    /**
     * This method returns all sets belonging to a current user
     * @param session current session
     * @return List of Sets available
     */
    @GetMapping("/choose_set")
    public List<Set> chooseSets(HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");

        return dataRepository.getAllSets(user.getLogin());
    }

    /**
     * This method deletes the set of name specified in path
     * @param name name of the set to be deleted
     * @param session current session
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteSet(@PathVariable("name") String name, HttpSession session){
        SessionUser user = (SessionUser) session.getAttribute("user");

        if (dataRepository.deleteSet(name, user.getLogin())){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/quiz/{set}")
    public List<Question> getQuestions(@PathVariable("set") String set, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        return dataRepository.getQuestions(sessionUser, set);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id") String id){
        if (dataRepository.deleteQuestion(id)) {
            System.out.println("deleted");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}