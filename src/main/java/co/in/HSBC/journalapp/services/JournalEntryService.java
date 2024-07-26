package co.in.HSBC.journalapp.services;

import co.in.HSBC.journalapp.entity.JournalEntry;
import co.in.HSBC.journalapp.entity.User;
import co.in.HSBC.journalapp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAll() {
        return journalRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findUserByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the user");
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalRepository.save(journalEntry);
    }

    public void removeEntry(ObjectId id, String username) {
        User user = userService.findUserByUsername(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalRepository.deleteById(id);
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalRepository.findById(id);
    }

    // Test method to run a minimal query
    public void testQuery() {
        Optional<JournalEntry> entry = journalRepository.findById(new ObjectId("6694005e274d10098f7eff58"));
        System.out.println(entry);
    }
}
