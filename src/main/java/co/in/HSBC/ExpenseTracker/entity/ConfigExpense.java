package co.in.HSBC.ExpenseTracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Config-ExpenseApp")
@Data
@NoArgsConstructor
public class ConfigExpense {

    private String key;
    private String value;


}
