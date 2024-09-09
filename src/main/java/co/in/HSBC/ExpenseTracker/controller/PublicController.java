package co.in.HSBC.ExpenseTracker.controller;

import co.in.HSBC.ExpenseTracker.Utils.JwtUtil;
import co.in.HSBC.ExpenseTracker.services.UserDetailsServiceImpl;
import co.in.HSBC.ExpenseTracker.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import co.in.HSBC.ExpenseTracker.entity.User;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/auth/api")
@Slf4j
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        try {
            boolean newUser = userService.saveNewUser(user);
            if (newUser) {
                String jwt = jwtUtil.generateToken(user.getUsername());
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-ExpenseTracker-Reg-JWT", jwt);
                return new ResponseEntity<>("User registered successfully. Welcome to Expense Tracker !" + headers, HttpStatus.CREATED);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
            }
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured while registering the user.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-ExpenseTracker-login-JWT", jwt);
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            log.error("Login failed for user {}: Invalid credentials", user.getUsername());
            return status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
