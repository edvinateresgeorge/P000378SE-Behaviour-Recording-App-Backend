package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.dto.RoutineRequest;
import com.behaviourbridgetracks.model.Routine;
import com.behaviourbridgetracks.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineRepository routineRepo;

    @PostMapping
    public ResponseEntity<Routine> create(
            @RequestBody RoutineRequest request,
            @AuthenticationPrincipal String userId) {

        Routine routine = Routine.builder()
                .userId(userId)
                .title(request.getTitle())
                .category(request.getCategory())
                .startDate(request.getStartDate())
                .startTime(request.getStartTime())
                .repeatDays(request.getRepeatDays() != null
                        ? request.getRepeatDays()
                        : new ArrayList<>())
                .steps(mapSteps(request.getSteps()))
                .endDate(request.getEndDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.status(201).body(routineRepo.save(routine));
    }

    @GetMapping
    public ResponseEntity<List<Routine>> getMyRoutines(
            @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(routineRepo.findByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestBody RoutineRequest request,
            @AuthenticationPrincipal String userId) {

        return routineRepo.findById(id)
                .filter(r -> r.getUserId().equals(userId))
                .map(r -> {
                    if (request.getTitle() != null)        r.setTitle(request.getTitle());
                    if (request.getCategory() != null)     r.setCategory(request.getCategory());
                    if (request.getStartTime() != null)    r.setStartTime(request.getStartTime());
                    if (request.getStartDate() != null)    r.setStartDate(request.getStartDate());
                    if (request.getEndDate() != null)      r.setEndDate(request.getEndDate());
                    if (request.getRepeatDays() != null)   r.setRepeatDays(request.getRepeatDays());
                    if (request.getSteps() != null)        r.setSteps(mapSteps(request.getSteps()));
                    r.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(routineRepo.save(r));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Routine> getById(
            @PathVariable String id,
            @AuthenticationPrincipal String userId) {
        return routineRepo.findById(id)
                .filter(r -> r.getUserId().equals(userId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable String id,
            @AuthenticationPrincipal String userId) {

        return routineRepo.findById(id)
                .filter(r -> r.getUserId().equals(userId))
                .map(r -> {
                    routineRepo.delete(r);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Helper ───────────────────────────────────────────────────────────────

    private List<Routine.Step> mapSteps(List<RoutineRequest.StepRequest> reqSteps) {
        if (reqSteps == null) return new ArrayList<>();
        return reqSteps.stream().map(s -> Routine.Step.builder()
                .title(s.getTitle())
                .durationMin(s.getDurationMin())
                .build()
        ).collect(Collectors.toList());
    }
}