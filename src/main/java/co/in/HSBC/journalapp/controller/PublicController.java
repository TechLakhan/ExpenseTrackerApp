package co.in.HSBC.journalapp.controller;

import co.in.HSBC.journalapp.entity.User;
import co.in.HSBC.journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }



}
