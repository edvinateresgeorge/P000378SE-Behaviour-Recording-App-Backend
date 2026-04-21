package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ClientRepository
        extends MongoRepository<Client, String> {

    // finds the child linked to a parent (CLIENT user)
    Optional<Client> findByUserId(String userId);
}