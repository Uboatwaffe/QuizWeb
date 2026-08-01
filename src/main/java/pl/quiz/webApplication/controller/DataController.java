package pl.quiz.webApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.servlet.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.objects.NewUser;
import pl.quiz.webApplication.objects.SessionUser;
import pl.quiz.webApplication.objects.User;


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
    @PostMapping("/login")
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

        SessionUser sessionUser = new SessionUser(login, role);

        if (user != null){
            session.setAttribute("user", sessionUser);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();

    }
}