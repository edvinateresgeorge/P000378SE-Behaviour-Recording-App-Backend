package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResponseRepository
        extends MongoRepository<Response, String> {}