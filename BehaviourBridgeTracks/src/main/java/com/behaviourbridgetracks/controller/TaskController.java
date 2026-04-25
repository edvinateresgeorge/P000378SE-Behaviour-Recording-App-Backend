package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.dto.TaskRequest;
import com.behaviourbridgetracks.model.Task;
import com.behaviourbridgetracks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepo;

    @PostMapping
    public ResponseEntity<Task> create(
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal String userId) {

        Task task = Task.builder()
                .userId(userId)
                .title(request.getTitle())
                .category(request.getCategory())
                .status(request.getStatus() != null ? request.getStatus() : "pending")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.status(201).body(taskRepo.save(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getMyTasks(
            @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(taskRepo.findByUserId(userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable String id,
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal String userId) {

        return taskRepo.findById(id)
                .filter(t -> t.getUserId().equals(userId))
                .map(t -> {
                    t.setStatus(request.getStatus());
                    t.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(taskRepo.save(t));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable String id,
            @AuthenticationPrincipal String userId) {

        return taskRepo.findById(id)
                .filter(t -> t.getUserId().equals(userId))
                .map(t -> {
                    taskRepo.delete(t);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
