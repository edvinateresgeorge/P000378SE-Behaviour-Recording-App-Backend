package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.dto.EmotionCheckInRequest;
import com.behaviourbridgetracks.model.EmotionCheckIn;
import com.behaviourbridgetracks.repository.EmotionCheckInRepository;
import com.behaviourbridgetracks.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/emotion-checkins")
@RequiredArgsConstructor
public class EmotionCheckInController {

    private final EmotionCheckInRepository checkInRepo;
    private final ClientRepository clientRepository;

    // Save a new emotion check-in
    // Called by parent from Flutter app
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody EmotionCheckInRequest request,
            @AuthenticationPrincipal String userId) {

        EmotionCheckIn checkIn = EmotionCheckIn.builder()
                .clientId(request.getClientId())
                .userId(userId)
                .emotion(request.getEmotion())
                .emotionLevel(request.getEmotionLevel())
                .notes(request.getNotes())
                .checkedAt(request.getCheckedAt() != null
                        ? request.getCheckedAt() : LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();

        return ResponseEntity.status(201)
                .body(checkInRepo.save(checkIn));
    }

    // Parent sees their child's check-in results
    // AC18.1 — view saved check-in results
    @GetMapping("/my-checkins")
    public ResponseEntity<?> getMyCheckIns(
            @AuthenticationPrincipal String userId) {

        // Get direct check-ins by this user
        List<EmotionCheckIn> direct =
                checkInRepo.findByUserId(userId);

        // Also get check-ins for their linked child
        List<EmotionCheckIn> clientCheckIns =
                clientRepository.findByUserId(userId)
                        .map(client ->
                                checkInRepo.findByClientId(client.getId()))
                        .orElse(new ArrayList<>());

        // Combine both
        List<EmotionCheckIn> combined = new ArrayList<>(direct);
        clientCheckIns.forEach(c -> {
            if (combined.stream()
                    .noneMatch(e -> e.getId().equals(c.getId()))) {
                combined.add(c);
            }
        });

        return ResponseEntity.ok(combined);
    }

    // ADMIN sees all check-ins across all clients
    @GetMapping("/all")
    public ResponseEntity<List<EmotionCheckIn>> getAll() {
        return ResponseEntity.ok(checkInRepo.findAll());
    }
}