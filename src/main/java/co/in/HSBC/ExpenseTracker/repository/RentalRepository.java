package co.in.HSBC.ExpenseTracker.repository;

import co.in.HSBC.ExpenseTracker.entity.Rental;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RentalRepository extends MongoRepository<Rental, ObjectId> {

}
