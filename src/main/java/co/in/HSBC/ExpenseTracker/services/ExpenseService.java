package co.in.HSBC.ExpenseTracker.services;

import co.in.HSBC.ExpenseTracker.entity.Expense;
import co.in.HSBC.ExpenseTracker.entity.User;
import co.in.HSBC.ExpenseTracker.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    @Transactional
    public void saveNewExpenditure(Expense expenditure, String username) {
        try {
            User user = userService.findUserByUsername(username);
            if (user == null) {
                log.error("User with username {} not found", username);
                throw new RuntimeException("User not found with the given username: " + username);
            }
            expenditure.setDate(LocalDateTime.now());
            Expense saved = expenseRepository.save(expenditure);
            if (user.getExpenses() == null) {
                user.setExpenses(new ArrayList<>());  // Initialize if null
            }
            user.getExpenses().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e) {
            log.error("error ", e);
            throw new RuntimeException("An error occurred while saving the user");
        }
    }

    public void saveExpenditure(Expense expenditure) {
        expenseRepository.save(expenditure);
    }

    @Transactional
    public boolean removeExpenditure(ObjectId id, String username) {
        boolean removed = false;
        try {
            User user = userService.findUserByUsername(username);
            removed = user.getExpenses().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                expenseRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("error ", e);
            throw new RuntimeException("An error occurred while deleting an expenditure. " + e);
        }
        return removed;
    }

    public Optional<Expense> findById(ObjectId id) {
        return expenseRepository.findById(id);
    }
    // Test method to run a minimal query
    public void testQuery() {
        Optional<Expense> expenditure = expenseRepository.findById(new ObjectId("6694005e274d10098f7eff58"));
        System.out.println(expenditure);
    }
}
