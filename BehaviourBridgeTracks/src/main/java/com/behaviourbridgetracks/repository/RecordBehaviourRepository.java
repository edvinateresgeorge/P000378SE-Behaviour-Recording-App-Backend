package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.RecordBehaviour;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RecordBehaviourRepository
        extends MongoRepository<RecordBehaviour, String> {
    List<RecordBehaviour> findByRecordId(String recordId);
}