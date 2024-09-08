package co.in.HSBC.ExpenseTracker.repository;

import co.in.HSBC.ExpenseTracker.entity.Expense;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense, ObjectId> {

}
