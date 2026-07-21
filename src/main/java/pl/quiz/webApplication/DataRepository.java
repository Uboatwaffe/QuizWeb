package pl.quiz.webApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Created on 21.07.2026</p>
 *
 * @author Maciej
 * @version 0.1
 */
@Repository
public class DataRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Test> test(){
        return mongoTemplate.find(new Query(), Test.class, "test");
    }
}
