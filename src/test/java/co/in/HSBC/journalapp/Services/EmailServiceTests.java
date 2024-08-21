package co.in.HSBC.journalapp.Services;

import co.in.HSBC.journalapp.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void testMailSender() {
        emailService.mailSender("shreep300@gmail.com", "Welcome To Journal App.", "Hi Shree, We welcome you to our journal application. Please use it & don't forget to write the review !");
    }
}
