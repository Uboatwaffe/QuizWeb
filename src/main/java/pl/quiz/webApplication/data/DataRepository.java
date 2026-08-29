package pl.quiz.webApplication.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import pl.quiz.webApplication.enums.Role;
import pl.quiz.webApplication.enums.Type;
import pl.quiz.webApplication.objects.Question;
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
     * This field is automatically set up by Spring Boot
     */
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * This method checks whether user had given correct credentials
     * @param username login
     * @return User of given details if exist in DB if not null
     */
    public User authenticateUser(String username) {
        Query query = Query.query(Criteria.where("username").is(username));

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

        String encodedPassword = passwordEncoder.encode(password);

        return mongoTemplate.insert(new User(login, encodedPassword, role), "user");

    }

    /**
     * This method checks if searched object exists
     * @param collection collection in which it will be looked for
     * @param key key of searched value
     * @param value searched value
     * @param classInstance instance of class of result
     * @return TRUE if existed, if not then FALSE
     */
    public boolean checkIfExists(String collection, String key, String value, String key2, String value2, @SuppressWarnings("rawtypes") Class classInstance) {
        Query query = new Query(Criteria
                .where(key).is(value)
                .and(key2).is(value2));

        //noinspection unchecked
        return !mongoTemplate.find(query, classInstance, collection).isEmpty();
    }

    /**
     * This method creates a question
     * @param question question itself
     * @param type type of the question (Type.java)
     * @param answer answer expected (Answer.java)
     * @param points how many points for question
     * @param set to which set should it belong
     * @param owner who is the owner of this question
     * @return Question.java
     */
    public Question addQuestion(String question, Type type, String answer, int points, String set, String owner){
        return mongoTemplate.insert(new Question(question, type, answer, points, set, owner), "question");
    }

    /**
     * This method return all sets of the current user
     * @param login login of the user
     * @return List of Sets available
     */
    public List<Set> getAllSets(String login){
        Query query = new Query(Criteria.where("owner").is(login));

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
     * @param login current user login
     * @param set set of the questions
     * @return List of questions
     */
    public List<Question> getQuestions(String login, String set) {
        Query query = new Query(Criteria
                .where("set").is(set)
                .and("owner").is(login));

        return mongoTemplate.find(query, Question.class, "question");
    }

    public Question getQuestionAnswerById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));

        return mongoTemplate.findOne(query, Question.class, "question");
    }

    public Set getSetFromId(String id) {
        Query query = new Query(Criteria.where("_id").is(id));

        query.fields()
                .include("set");

        return mongoTemplate.findOne(query, Set.class, "question");
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

    /**
     * This method updates a question
     * @param question details of a question
     * @return TRUE if successful, if not then FALSE
     */
    public boolean updateQuestion(Question question){
        Query query = new Query(Criteria.where("_id").is(question.getId()));

        Update update = new Update();
        update.set("question", question.getQuestion());
        update.set("answer", question.getAnswer());
        update.set("points", question.getPoints());
        update.set("type", question.getType());

        return mongoTemplate.updateFirst(query, update, "question").wasAcknowledged();

    }

    /**
     * This method cheks whether answer provided by user is correct
     * @param id id of the question
     * @param userAnswer answer provided
     * @return number of points if answered correctly, if not then 0
     */
    public int checkAnswer(String id, String userAnswer){
        Query query = new Query(Criteria.where("_id").is(id));

        query.fields()
                .include("points")
                .include("answer");

        Question question = mongoTemplate.findOne(query, Question.class, "question");

        if (question == null) {
            return 0;
        }

        if (question.getAnswer().equals(userAnswer)) {
            return question.getPoints();
        } else {
            return 0;
        }
    }

    /**
     * This method returns number of points that can be achieved when playing specified set
     * @param set specified set
     * @param sessionUser current sessionUser
     * @return number of points available to be collected
     */
    public int allPointsInSet(Set set, String username) {
        Query query = new Query(Criteria
                .where("set").is(set.getName())
                .and("owner").is(username));

        List<Question> questions = mongoTemplate.find(query, Question.class, "question");

        int score = 0;

        for  (Question question : questions) {
            score += question.getPoints();
        }

        return score;
    }

    /**
     * This method returns every user in database
     *
     * @return List of user
     */
    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class, "user");
    }

    /**
     * This method deletes user of specified id
     * @param id id of the user to be deleted
     * @return TRUE if successful, if not then FALSE
     */
    public boolean deleteUser(String id) {
        Query query = new Query(Criteria.where("_id").is(id));

        return mongoTemplate.remove(query, "user").wasAcknowledged();
    }

    /**
     * This method returns every set in database
     * @return List of set
     */
    public List<Set> getEverySet() {
        Query query = new Query();

        query.fields()
                .include("set");

        List<Set> sets = mongoTemplate.find(query, Set.class, "question");

        List<Set> removedDuplicates = new ArrayList<>();

        for (Set set : sets) {
            if (!removedDuplicates.contains(set)) {
                removedDuplicates.add(set);
            }
        }

        return removedDuplicates;
    }

    /**
     * This method deletes set without checking if the current user is the owner
     * @param name name of the set to be deleted
     * @return TRUE if successful, if not then FALSE
     */
    public boolean deleteSetNoAuth(String name) {
        Query query = new Query(Criteria
                .where("set").is(name));

        return mongoTemplate.remove(query, "question").wasAcknowledged();
    }

    /**
     * This method updates a user
     * @param user new user details
     * @return TRUE if successful, if not then FALSE
     */
    public boolean updateUser(User user) {
        Query query = new Query(Criteria.where("_id").is(user.getId()));

        Update update = new Update();
        update.set("username", user.getLogin());
        update.set("role", user.getRole());

        return mongoTemplate.updateFirst(query, update, "user").wasAcknowledged();
    }

}
