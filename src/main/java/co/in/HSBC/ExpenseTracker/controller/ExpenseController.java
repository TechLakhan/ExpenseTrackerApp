package co.in.HSBC.ExpenseTracker.controller;

import co.in.HSBC.ExpenseTracker.entity.Expense;
import co.in.HSBC.ExpenseTracker.entity.User;
import co.in.HSBC.ExpenseTracker.services.ExpenseService;
import co.in.HSBC.ExpenseTracker.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
@Slf4j
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllExpensesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        List<Expense> all = user.getExpenses();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>("List of all expenses so far : " + all, HttpStatus.OK);
        }
        return new ResponseEntity<>("No expenses found for the Username : " + username, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createExpenditure(@RequestBody Expense myExpenditure) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            expenseService.saveNewExpenditure(myExpenditure, username);
            return new ResponseEntity<>("Expenditure added to the username : " + username, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getExpenditureById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        List<Expense> collect = user.getExpenses().stream().filter(x -> x.getId().equals(id)).toList();
        if (!collect.isEmpty()) {
            Optional<Expense> expense = expenseService.findById(id);
            if (expense.isPresent()) {
                return new ResponseEntity<>("Expenditure for the given id " + expense.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Not Found any expenditure for the given id ", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpenditureByTitle(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = expenseService.removeExpenditure(id, username);
        if (removed) {
            return new ResponseEntity<>("Expense Deleted Successfully ! ", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateExpenditureById(@PathVariable ObjectId id, @RequestBody Expense newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        List<Expense> collect = user.getExpenses().stream().filter(x -> x.getId().equals(id)).toList();
        if (!collect.isEmpty()) {
            Optional<Expense> expense = expenseService.findById(id);
            if (expense.isPresent()) {
                Expense oldEntry = expense.get();
                oldEntry.setAmount(newEntry.getAmount() > 0.0 ? newEntry.getAmount() : oldEntry.getAmount());
                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
                expenseService.saveExpenditure(oldEntry);
                return new ResponseEntity<>("Expenditure updated successfully. " + oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
