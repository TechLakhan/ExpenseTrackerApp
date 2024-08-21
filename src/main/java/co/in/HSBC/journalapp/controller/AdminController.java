package co.in.HSBC.journalapp.controller;

import co.in.HSBC.journalapp.Cache.AppCache;
import co.in.HSBC.journalapp.entity.User;
import co.in.HSBC.journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAll() {
        List<User> all = userService.getAll();
        if ( all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return null;
    }

    @PostMapping("/create-new-admin")
    public void createUser(@RequestBody User user) {
        userService.saveNewAdmin(user);
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache() {
        appCache.initCache();
    }
}
