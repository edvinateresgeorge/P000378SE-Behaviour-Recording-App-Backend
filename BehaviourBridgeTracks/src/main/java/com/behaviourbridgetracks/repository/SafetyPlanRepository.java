package com.behaviourbridgetracks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.behaviourbridgetracks.model.SafetyPlan;

public interface SafetyPlanRepository extends MongoRepository<SafetyPlan, String> {
    Optional<SafetyPlan> findFirstByUserId(String userId);
    List<SafetyPlan> findAllByUserId(String userId);
}