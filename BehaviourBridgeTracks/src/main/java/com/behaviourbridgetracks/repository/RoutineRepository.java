package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RoutineRepository extends MongoRepository<Routine, String> {
    List<Routine> findByUserId(String userId);
}