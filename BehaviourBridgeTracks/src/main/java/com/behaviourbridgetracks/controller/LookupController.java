package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.model.*;
import com.behaviourbridgetracks.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lookups")
@RequiredArgsConstructor
public class LookupController {

    private final BehaviourTypeRepository behaviourTypeRepo;
    private final TriggerRepository triggerRepo;
    private final SettingRepository settingRepo;
    private final ResponseRepository responseRepo;

    @GetMapping("/behaviour-types")
    public List<BehaviourType> getBehaviourTypes() {
        return behaviourTypeRepo.findAll();
    }

    @GetMapping("/triggers")
    public List<Trigger> getTriggers() {
        return triggerRepo.findAll();
    }

    @GetMapping("/settings")
    public List<Setting> getSettings() {
        return settingRepo.findAll();
    }

    @GetMapping("/responses")
    public List<Response> getResponses() {
        return responseRepo.findAll();
    }
}