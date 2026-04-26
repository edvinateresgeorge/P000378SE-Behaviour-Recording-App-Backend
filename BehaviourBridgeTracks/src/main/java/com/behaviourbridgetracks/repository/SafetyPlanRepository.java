package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.SafetyPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SafetyPlanRepository
        extends MongoRepository<SafetyPlan, String> {
    // Return List to avoid error when multiple records exist
    List<SafetyPlan> findByUserId(String userId);
}