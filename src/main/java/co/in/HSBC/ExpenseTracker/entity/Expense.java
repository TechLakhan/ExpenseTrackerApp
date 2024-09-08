package co.in.HSBC.ExpenseTracker.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Expenses")
@Data
@NoArgsConstructor
public class Expense {
    @Id
    private ObjectId id;

    private double amount;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;

}
