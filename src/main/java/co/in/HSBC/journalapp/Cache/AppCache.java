package co.in.HSBC.journalapp.Cache;

import co.in.HSBC.journalapp.entity.ConfigJournal;
import co.in.HSBC.journalapp.repository.ConfigJournalAppRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    public Map<String, String> appCache;

    @PostConstruct
    public void initCache() {
        appCache = new HashMap<>();
        List<ConfigJournal> keys = configJournalAppRepo.findAll();
        for (ConfigJournal configJournal : keys) {
            appCache.put(configJournal.getKey(), configJournal.getValue());
        }
    }
}
