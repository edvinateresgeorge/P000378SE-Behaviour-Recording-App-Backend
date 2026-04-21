package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.BehaviourType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BehaviourTypeRepository
        extends MongoRepository<BehaviourType, String> {}