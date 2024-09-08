package co.in.HSBC.ExpenseTracker.repository;

import co.in.HSBC.ExpenseTracker.entity.ConfigExpense;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigExpenseAppRepo extends MongoRepository<ConfigExpense, ObjectId> {

}
