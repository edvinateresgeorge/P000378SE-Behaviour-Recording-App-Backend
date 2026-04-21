package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.BehaviourRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BehaviourRecordRepository
        extends MongoRepository<BehaviourRecord, String> {
    List<BehaviourRecord> findByUserId(String userId);
    List<BehaviourRecord> findByClientId(String clientId);
}