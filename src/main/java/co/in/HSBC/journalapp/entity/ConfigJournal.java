package co.in.HSBC.journalapp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Config-journalApp")
@Data
@NoArgsConstructor
public class ConfigJournal {

    private String key;
    private String value;


}
