package co.in.HSBC.journalapp.controller;

import co.in.HSBC.journalapp.api.response.WeatherResponse;
import co.in.HSBC.journalapp.services.WeatherService;
import co.in.HSBC.journalapp.entity.User;
import co.in.HSBC.journalapp.repository.UserRepository;
import co.in.HSBC.journalapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findUserByUsername(username);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeatherForecast("Mumbai");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", weather feels like " + weatherResponse.getCurrent().getTemperature() + " degree celsius !";
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting,  HttpStatus.OK);
    }
}
