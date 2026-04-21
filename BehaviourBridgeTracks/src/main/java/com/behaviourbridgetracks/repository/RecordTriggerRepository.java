package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.RecordTrigger;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RecordTriggerRepository
        extends MongoRepository<RecordTrigger, String> {
    List<RecordTrigger> findByRecordId(String recordId);
}