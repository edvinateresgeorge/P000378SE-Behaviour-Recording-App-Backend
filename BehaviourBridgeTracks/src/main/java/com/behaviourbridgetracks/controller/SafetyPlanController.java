package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.dto.SafetyPlanRequest;
import com.behaviourbridgetracks.model.Contact;
import com.behaviourbridgetracks.model.SafetyPlan;
import com.behaviourbridgetracks.repository.SafetyPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/safety-plan")
@RequiredArgsConstructor
public class SafetyPlanController {

    private final SafetyPlanRepository repo;

    @GetMapping
    public ResponseEntity<SafetyPlan> get(@AuthenticationPrincipal String userId) {
        List<SafetyPlan> all = repo.findAllByUserId(userId);
        if (all.size() > 1) {
            repo.deleteAll(all.subList(1, all.size()));
        }

        SafetyPlan plan = all.isEmpty() ? null : all.get(0);
        if (plan == null) plan = repo.findFirstByUserId(userId).orElseGet(() ->
                SafetyPlan.builder()
                        .userId(userId)
                        .contacts(new ArrayList<>())
                        .warningSigns(new ArrayList<>())
                        .calmingStrategies(new ArrayList<>())
                        .safeSpaces(new ArrayList<>())
                        .notes("")
                        .build()
        );
        return ResponseEntity.ok(plan);
    }

    @PostMapping
    public ResponseEntity<SafetyPlan> save(
            @RequestBody SafetyPlanRequest request,
            @AuthenticationPrincipal String userId) {

        SafetyPlan plan = repo.findFirstByUserId(userId)
                .orElseGet(() -> SafetyPlan.builder().userId(userId).build());

        List<Contact> contacts = request.getContacts() == null ? new ArrayList<>() :
                request.getContacts().stream()
                        .map(dto -> Contact.builder()
                                .name(dto.getName())
                                .number(dto.getNumber())
                                .build())
                        .collect(Collectors.toList());

        plan.setContacts(contacts);
        plan.setWarningSigns(request.getWarningSigns() != null ? request.getWarningSigns() : new ArrayList<>());
        plan.setCalmingStrategies(request.getCalmingStrategies() != null ? request.getCalmingStrategies() : new ArrayList<>());
        plan.setSafeSpaces(request.getSafeSpaces() != null ? request.getSafeSpaces() : new ArrayList<>());
        plan.setNotes(request.getNotes() != null ? request.getNotes() : "");

        return ResponseEntity.ok(repo.save(plan));
    }
}
