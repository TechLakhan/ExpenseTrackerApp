package co.in.HSBC.journalapp.repository;

import co.in.HSBC.journalapp.entity.ConfigJournal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournal, ObjectId> {

}
