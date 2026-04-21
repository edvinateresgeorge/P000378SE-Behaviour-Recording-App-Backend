package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.dto.BehaviourRecordRequest;
import com.behaviourbridgetracks.model.*;
import com.behaviourbridgetracks.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/behaviour-records")
@RequiredArgsConstructor
public class BehaviourRecordController {

    private final BehaviourRecordRepository recordRepo;
    private final RecordBehaviourRepository recBehaviourRepo;
    private final RecordTriggerRepository recTriggerRepo;
    private final RecordSettingRepository recSettingRepo;
    private final RecordResponseRepository recResponseRepo;
    private final ClientRepository clientRepository;

    // Both ADMIN and CLIENT can save a behaviour record
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody BehaviourRecordRequest request,
            @AuthenticationPrincipal String userId) {

        BehaviourRecord record = BehaviourRecord.builder()
                .clientId(request.getClientId())
                .userId(userId)
                .intensity(request.getIntensity())
                .occurredAt(request.getOccurredAt() != null
                        ? request.getOccurredAt() : LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .notes(request.getNotes())
                .location(request.getLocation())
                .behaviourNames(request.getBehaviourNames())
                .triggerNames(request.getTriggerNames())
                .strategyNames(request.getStrategyNames())
                .replacementNames(request.getReplacementNames())
                .build();

        BehaviourRecord saved = recordRepo.save(record);

        if (request.getBehaviourTypeIds() != null) {
            request.getBehaviourTypeIds().forEach(id ->
                    recBehaviourRepo.save(RecordBehaviour.builder()
                            .recordId(saved.getId())
                            .behaviourTypeId(id).build()));
        }
        if (request.getTriggerIds() != null) {
            request.getTriggerIds().forEach(id ->
                    recTriggerRepo.save(RecordTrigger.builder()
                            .recordId(saved.getId())
                            .triggerId(id).build()));
        }
        if (request.getSettingIds() != null) {
            request.getSettingIds().forEach(id ->
                    recSettingRepo.save(RecordSetting.builder()
                            .recordId(saved.getId())
                            .settingId(id).build()));
        }
        if (request.getResponseIds() != null) {
            request.getResponseIds().forEach(id ->
                    recResponseRepo.save(RecordResponse.builder()
                            .recordId(saved.getId())
                            .responseId(id).build()));
        }

        return ResponseEntity.status(201).body(saved);
    }

    // CLIENT — see their own records regardless of client linking
    @GetMapping("/my-records")
    public ResponseEntity<?> getMyRecords(
            @AuthenticationPrincipal String userId) {

        // First try to find records saved directly by this user
        List<BehaviourRecord> directRecords =
                recordRepo.findByUserId(userId);

        // Also try to find records for their linked client
        List<BehaviourRecord> clientRecords =
                clientRepository.findByUserId(userId)
                        .map(client -> recordRepo.findByClientId(client.getId()))
                        .orElse(new ArrayList<>());

        // Combine both lists and remove duplicates
        Set<String> seen = new HashSet<>();
        List<BehaviourRecord> combined = new ArrayList<>();

        for (BehaviourRecord r : directRecords) {
            if (seen.add(r.getId())) combined.add(r);
        }
        for (BehaviourRecord r : clientRecords) {
            if (seen.add(r.getId())) combined.add(r);
        }

        return ResponseEntity.ok(combined);
    }

    // ADMIN only — see ALL records across ALL clients
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BehaviourRecord>> getAll() {
        return ResponseEntity.ok(recordRepo.findAll());
    }


    // Both roles — get one record by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable String id,
            @AuthenticationPrincipal String userId) {
        return recordRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}