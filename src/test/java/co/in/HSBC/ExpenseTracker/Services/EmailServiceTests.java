package co.in.HSBC.ExpenseTracker.Services;

import co.in.HSBC.ExpenseTracker.entity.User;
import co.in.HSBC.ExpenseTracker.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testMailSender() {
        User lakhan = User.builder().username("Lakhan").password("abcd").email("lakhan.patil008@gmail.com").build();
        emailService.mailSender(lakhan,"shreep300@gmail.com", "Welcome To Journal App.", "Hi Shree, We welcome you to our daily expense tracker application. Don't' forget to share it with your friends & keep tracking your expenses.");
    }
}
