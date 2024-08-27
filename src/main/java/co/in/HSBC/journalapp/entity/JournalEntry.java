package co.in.HSBC.journalapp.entity;

import co.in.HSBC.journalapp.Enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journalEntry")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String content;

    private Sentiment sentiment;

    private LocalDateTime date;

}
