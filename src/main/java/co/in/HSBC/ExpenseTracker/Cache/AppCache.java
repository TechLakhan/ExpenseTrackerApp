package co.in.HSBC.ExpenseTracker.Cache;

import co.in.HSBC.ExpenseTracker.entity.ConfigExpense;
import co.in.HSBC.ExpenseTracker.repository.ConfigExpenseAppRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        weather_api;
    }

    @Autowired
    private ConfigExpenseAppRepo configJournalAppRepo;

    public Map<String, String> appCache;

    @PostConstruct
    public void initCache() {
        appCache = new HashMap<>();
        List<ConfigExpense> keys = configJournalAppRepo.findAll();
        for (ConfigExpense configExpense : keys) {
            appCache.put(configExpense.getKey(), configExpense.getValue());
        }
    }
}
