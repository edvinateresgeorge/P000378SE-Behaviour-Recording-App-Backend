package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.model.CrisisContact;
import com.behaviourbridgetracks.model.SafetyPlan;
import com.behaviourbridgetracks.repository.SafetyPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/safety-plan")
@RequiredArgsConstructor
public class SafetyPlanController {

    private final SafetyPlanRepository safetyPlanRepo;

    // Get safety plan contacts for logged-in user
    @GetMapping
    public ResponseEntity<?> getSafetyPlan(
            @AuthenticationPrincipal String userId) {

        // Get all plans for this user
        List<SafetyPlan> plans = safetyPlanRepo.findByUserId(userId);

        // Return first one if exists
        // Otherwise return default with crisis helplines
        SafetyPlan plan = plans.isEmpty() ?
                SafetyPlan.builder()
                        .userId(userId)
                        .emergencyContacts(List.of())
                        .warningSigns(List.of())
                        .whatHelps(List.of())
                        .safeSpaces(List.of())
                        .crisisHelplines(List.of(
                                CrisisContact.builder()
                                        .name("Kids Helpline")
                                        .number("1800 55 1800")
                                        .action("Call").build(),
                                CrisisContact.builder()
                                        .name("Lifeline")
                                        .number("13 11 14")
                                        .action("Call").build(),
                                CrisisContact.builder()
                                        .name("Beyond Blue")
                                        .number("1300 22 4636")
                                        .action("Call").build(),
                                CrisisContact.builder()
                                        .name("Crisis Text Line")
                                        .number("0477 13 11 14")
                                        .action("Text").build()
                        ))
                        .additionalNotes("")
                        .build()
                : plans.get(0);

        return ResponseEntity.ok(plan);
    }

    // Save or update safety plan
    @PostMapping
    public ResponseEntity<?> saveSafetyPlan(
            @RequestBody SafetyPlan plan,
            @AuthenticationPrincipal String userId) {
        plan.setUserId(userId);

        // Add default Australian crisis helplines
        // if not provided in request
        if (plan.getCrisisHelplines() == null ||
                plan.getCrisisHelplines().isEmpty()) {
            plan.setCrisisHelplines(List.of(
                    CrisisContact.builder()
                            .name("Kids Helpline")
                            .number("1800 55 1800")
                            .action("Call").build(),
                    CrisisContact.builder()
                            .name("Lifeline")
                            .number("13 11 14")
                            .action("Call").build(),
                    CrisisContact.builder()
                            .name("Beyond Blue")
                            .number("1300 22 4636")
                            .action("Call").build(),
                    CrisisContact.builder()
                            .name("Crisis Text Line")
                            .number("0477 13 11 14")
                            .action("Text").build()
            ));
        }

        return ResponseEntity.status(201)
                .body(safetyPlanRepo.save(plan));
    }
}