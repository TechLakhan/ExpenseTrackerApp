package co.in.HSBC.journalapp.services;

import co.in.HSBC.journalapp.entity.User;
import co.in.HSBC.journalapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            log.error("Exception occured ", e);
            return false;
        }
    }

    public void saveNewAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void removeUser(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User authenticateUser(String email, String password) throws Exception {
        User user = userRepository.findUserByEmail(email);
        if ( user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid Credentials");
        }
        return user;
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
