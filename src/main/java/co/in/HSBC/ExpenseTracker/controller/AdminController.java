package co.in.HSBC.ExpenseTracker.controller;

import co.in.HSBC.ExpenseTracker.Cache.AppCache;
import co.in.HSBC.ExpenseTracker.entity.User;
import co.in.HSBC.ExpenseTracker.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/allUsers/api")
    public ResponseEntity<?> getAll() {
        List<User> all = userService.getAll();
        if ( all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return null;
    }

    @PostMapping("/createNewAdmin")
    public void createUser(@RequestBody User user) {
        userService.saveNewAdmin(user);
        log.info("New admin user is created.");
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache() {
        appCache.initCache();
        log.info("app cache cleared successfully.");
    }
}
