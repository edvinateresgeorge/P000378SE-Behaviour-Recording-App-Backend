package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.EmotionCheckIn;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EmotionCheckInRepository
        extends MongoRepository<EmotionCheckIn, String> {
    List<EmotionCheckIn> findByUserId(String userId);
    List<EmotionCheckIn> findByClientId(String clientId);
}