package co.in.HSBC.journalapp.controller;

import co.in.HSBC.journalapp.entity.JournalEntry;
import co.in.HSBC.journalapp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() { // localhost:8080/journal -> get
        return journalEntryService.getAll();
    }

    @PostMapping("/addnew")
    public boolean createEntry(@RequestBody JournalEntry myEntry) { // localhost:8080/journal -> post
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId id) {
        return journalEntryService.findById(id).orElse(null);
    }

    @DeleteMapping("/delete/{myId}")
    public boolean deleteJournalEntryByTitle(@PathVariable ObjectId myId, JournalEntry myEntry) {
        journalEntryService.removeEntry(myId);
        return true;
    }

    @PutMapping("/update/{id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry oldEntry = journalEntryService.findById(id).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }
}
