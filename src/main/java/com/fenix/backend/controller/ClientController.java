package com.fenix.backend.controller;

import com.fenix.backend.domain.entity.Client;
import com.fenix.backend.domain.enums.ClientStatus;
import com.fenix.backend.dto.request.ClientRequest;
import com.fenix.backend.dto.response.ClientResponse;
import com.fenix.backend.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll() {
        List<ClientResponse> response = clientService.findAll().stream()
                .map(ClientResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ClientResponse.from(clientService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest request) {
        Client client = Client.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .cpf(request.cpf())
                .build();
        Client created = clientService.create(client);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(ClientResponse.from(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody ClientRequest request) {
        Client data = Client.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .cpf(request.cpf())
                .build();
        return ResponseEntity.ok(ClientResponse.from(clientService.update(id, data)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ClientResponse> changeStatus(@PathVariable Long id,
                                                       @RequestBody ClientStatus status) {
        return ResponseEntity.ok(ClientResponse.from(clientService.changeStatus(id, status)));
    }
}
