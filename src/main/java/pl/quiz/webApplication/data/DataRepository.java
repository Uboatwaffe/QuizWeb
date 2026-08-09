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

import java.util.ArrayList;
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

    /**
     * This method checkes if searched object exists
     * @param collection collection in which it will be looked for
     * @param key key of searched value
     * @param value searched value
     * @param classInstance instance of class of result
     * @return TRUE if existed, if not then FALSE
     */
    public boolean checkIfExists(String collection, String key, String value, String key2, String value2, Class classInstance){
        Query query = new Query(Criteria
                .where(key).is(value)
                .and(key2).is(value2));

        return !mongoTemplate.find(query, classInstance, collection).isEmpty();
    }

    /**
     * This method creates a question
     * @param starting is this a first question in a set? (are you ready question)
     * @param question question itself
     * @param type type of the question (Type.java)
     * @param answer answer expected (Answer.java)
     * @param points how many points for question
     * @param set to which set should it belong
     * @param owner who is the owner of this question
     * @return Question.java
     */
    public Question addQuestion(String question, Type type, Answer answer, int points, String set, String owner){
        return mongoTemplate.insert(new Question(question, type, answer, points, set, owner), "question");
    }

    /**
     * This method return all sets of the current user
     * @param login login of the user
     * @return List of Sets available
     */
    public List<Set> getAllSets(String login){
        Query query = new Query();

        query.fields()
                .include("set");

        List<Set> sets = mongoTemplate.find(query, Set.class, "question");

        List<Set> removedDuplicates = new ArrayList<>();

        for (Set set: sets) {
            if (!removedDuplicates.contains(set)) {
                removedDuplicates.add(set);
            }
        }

        return removedDuplicates;
    }

    /**
     * This method deletes a set
     * @param name name of the set to be deleted
     * @param login login of the current user
     * @return TRUE if successful, if not then FALSE
     */
    public boolean deleteSet(String name, String login){
        Query query = new Query(Criteria
                .where("set").is(name)
                .and("owner").is(login));

        return mongoTemplate.remove(query, "question").wasAcknowledged();
    }

    /**
     * This method returns list of all questions in specified set
     * @param sessionUser current user details
     * @param set set of the questions
     * @return List of questions
     */
    public List<Question> getQuestions(SessionUser sessionUser, String set){
        Query query = new Query(Criteria
                .where("set").is(set)
                .and("owner").is(sessionUser.getLogin()));

        return mongoTemplate.find(query, Question.class, "question");
    }

    /**
     * This method deletes question
     * @param id id of the question to be deleted
     * @return TRUE if successful, if not then FALSE
     */
    public boolean deleteQuestion(String id){
        Query query = new Query(Criteria.where("_id").is(id));

        return mongoTemplate.remove(query, "question").wasAcknowledged();
    }


}
