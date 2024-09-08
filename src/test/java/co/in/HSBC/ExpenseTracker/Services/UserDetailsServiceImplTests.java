package co.in.HSBC.ExpenseTracker.Services;

import co.in.HSBC.ExpenseTracker.entity.User;
import co.in.HSBC.ExpenseTracker.services.UserDetailsServiceImpl;
import co.in.HSBC.ExpenseTracker.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ActiveProfiles("develop")
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        when (userRepository.findUserByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("Ram").password("jfhjkhdjs").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("Ram");
        Assertions.assertNotNull(user);
    }
}
