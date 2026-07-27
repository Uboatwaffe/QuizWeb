package pl.quiz.webApplication.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


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


}
