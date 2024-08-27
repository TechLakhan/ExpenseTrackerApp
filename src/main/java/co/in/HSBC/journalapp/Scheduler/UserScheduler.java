package co.in.HSBC.journalapp.Scheduler;

import co.in.HSBC.journalapp.Cache.AppCache;
import co.in.HSBC.journalapp.Enums.Sentiment;
import co.in.HSBC.journalapp.entity.JournalEntry;
import co.in.HSBC.journalapp.entity.User;
import co.in.HSBC.journalapp.repository.UserRepositoryImpl;
import co.in.HSBC.journalapp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x -> x.getSentiment()).toList();
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
//                message for sentimental analysis !
                String message = "Hi " + user.getUsername() + ", Your mood for this week is "
                        + mostFrequentSentiment.toString() + ". Have a lovely day !";
                emailService.mailSender(user, user.getEmail(), "Sentimental Analysis", message);
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.initCache();
    }
}
