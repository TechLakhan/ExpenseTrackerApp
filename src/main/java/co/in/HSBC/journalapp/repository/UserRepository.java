package co.in.HSBC.journalapp.repository;

import co.in.HSBC.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    void deleteByUsername(String username);
}
