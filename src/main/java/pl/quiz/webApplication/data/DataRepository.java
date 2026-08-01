package pl.quiz.webApplication.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.objects.User;

/**
 * This class is responsible for sending queries to the database
 * <p>Created on 21.07.2026</p>
 * @author Maciej
 * @version 0.1
 */
@Repository
public class DataRepository {

    /**
     * This field is automatically set up by Spring Boot
     */
    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * This method checks whether user had given correct credentials
     * @param username login
     * @param password password
     * @return User of given details if exist in DB if not null
     */
    public User authenticateUser(String username, String password){
        Query query = Query.query(Criteria.where("username").is(username).and("password").is(password));

        return mongoTemplate.findOne(query, User.class, "user");
    }

    /**
     * This method adds user of listed details to DB
     * @param login login
     * @param password password
     * @param role role (ADMIN or USER)
     * @return User of given details
     */
    public User addUser(String login, String password, Role role){

        return mongoTemplate.insert(new User(login, password, role));

    }
}
