package co.in.HSBC.journalapp.services;

import co.in.HSBC.journalapp.entity.JournalEntry;
import co.in.HSBC.journalapp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalRepository journalRepository;

    public List<JournalEntry> getAll() {
        return journalRepository.findAll();
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalRepository.save(journalEntry);
    }

    public void removeEntry(ObjectId id) {
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
