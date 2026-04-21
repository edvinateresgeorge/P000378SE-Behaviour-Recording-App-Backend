package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.model.Client;
import com.behaviourbridgetracks.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;

    // ADMIN ONLY — practitioner sees ALL clients
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientRepository.findAll());
    }

    // ADMIN ONLY — practitioner adds a new client (child)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> addClient(
            @RequestBody Client client,
            @AuthenticationPrincipal String userId) {
        client.setUserId(userId); // track which staff added them
        return ResponseEntity
                .status(201)
                .body(clientRepository.save(client));
    }

    // ADMIN ONLY — get one client by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getClientById(@PathVariable String id) {
        return clientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CLIENT (parent) — get only their own linked client profile
    @GetMapping("/my-child")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> getMyChild(
            @AuthenticationPrincipal String userId) {
        return clientRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ADMIN — link a client to a parent user
    @PutMapping("/{clientId}/link/{parentUserId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> linkClientToParent(
            @PathVariable String clientId,
            @PathVariable String parentUserId) {

        return clientRepository.findById(clientId)
                .map(client -> {
                    client.setUserId(parentUserId);
                    clientRepository.save(client);
                    return ResponseEntity.ok("Client linked to parent");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}