package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingRepository
        extends MongoRepository<Setting, String> {}