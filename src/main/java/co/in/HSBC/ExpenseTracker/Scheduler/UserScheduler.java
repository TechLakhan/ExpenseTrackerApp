package co.in.HSBC.ExpenseTracker.Scheduler;

import co.in.HSBC.ExpenseTracker.Cache.AppCache;
import co.in.HSBC.ExpenseTracker.entity.Expense;
import co.in.HSBC.ExpenseTracker.entity.User;
import co.in.HSBC.ExpenseTracker.repository.UserRepositoryImpl;
import co.in.HSBC.ExpenseTracker.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    //    @Scheduled(cron = "0 0 12 ? * SUN")
    public void fetchUserAndSendMail() {
        List<User> users = userRepository.getUsersWithQueries();
        for (User user : users) {
            List<Expense> expenses = user.getExpenses();
            double totalExpenditure = expenses.stream().mapToDouble(Expense::getAmount).sum();
//                message for sentimental analysis !
                String message = "Hi " + user.getUsername() + ", Your expenditure for this week is "
                        + totalExpenditure + ". Keep the track of your expenses carefully !";
                emailService.mailSender(user, user.getEmail(), "Weekly Expense Summary", message);
            }
        }
        @Scheduled(cron = "0 0/10 * ? * *")
        public void clearAppCache() {
        appCache.initCache();
    }
}
