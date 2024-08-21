package co.in.HSBC.journalapp.Repository;

import co.in.HSBC.journalapp.repository.UserRepositoryImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Disabled
    @Test
    public void getUsersWithQueriesTest() {
        userRepository.getUsersWithQueries();
    }
}
