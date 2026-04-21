package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.Trigger;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TriggerRepository
        extends MongoRepository<Trigger, String> {}
