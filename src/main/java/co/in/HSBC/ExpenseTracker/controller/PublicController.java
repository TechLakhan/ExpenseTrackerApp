package co.in.HSBC.ExpenseTracker.controller;

import co.in.HSBC.ExpenseTracker.entity.User;
import co.in.HSBC.ExpenseTracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthcheck() {
        return "ok";
    }

    @CrossOrigin(origins = "http://localhost:3000") // Allow only requests from React app running on localhost:3000
    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/register")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }



}
