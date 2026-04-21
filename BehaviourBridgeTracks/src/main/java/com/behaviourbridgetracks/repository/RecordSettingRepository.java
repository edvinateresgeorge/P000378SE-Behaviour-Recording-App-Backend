package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.RecordSetting;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RecordSettingRepository
        extends MongoRepository<RecordSetting, String> {
    List<RecordSetting> findByRecordId(String recordId);
}