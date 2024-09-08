package co.in.HSBC.ExpenseTracker.repository;

import co.in.HSBC.ExpenseTracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUsersWithQueries() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").exists(true).ne(null));
        query.addCriteria(Criteria.where("notificationEnabled").is(true));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
