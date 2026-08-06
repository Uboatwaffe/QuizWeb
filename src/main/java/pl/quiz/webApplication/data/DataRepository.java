package pl.quiz.webApplication.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pl.quiz.webApplication.enums.Answer;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.enums.Type;
import pl.quiz.webApplication.objects.Question;
import pl.quiz.webApplication.objects.SessionUser;
import pl.quiz.webApplication.objects.Set;
import pl.quiz.webApplication.objects.User;

import java.util.List;

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

        Query query = new Query(Criteria.where("username").is(login));

        if (!mongoTemplate.find(query, User.class, "user").isEmpty()) {
            return null;
        }

        return mongoTemplate.insert(new User(login, password, role), "user");

    }

    public boolean checkIfExists(String collection, String key, String value, Class classInstance){
        Query query = new Query(Criteria.where(key).is(value));

        return !mongoTemplate.find(query, classInstance, collection).isEmpty();
    }

    public Question addQuestion(boolean starting, String question, Type type, Answer answer, int points, String set, String owner){
        return mongoTemplate.insert(new Question(starting, question, type, answer, points, set, owner), "question");
    }

    public List<Set> getAllSets(String login){
        Query query = new Query(Criteria
                .where("startingQuestion").is(true)
                .and("owner").is(login));

        query.fields()
                .include("set");


        return mongoTemplate.find(query, Set.class, "question");
    }

    public boolean deleteSet(String name, String login){
        Query query = new Query(Criteria
                .where("set").is(name)
                .and("owner").is(login));

        return mongoTemplate.remove(query, "question").wasAcknowledged();
    }

}
