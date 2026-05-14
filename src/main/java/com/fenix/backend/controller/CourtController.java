package com.fenix.backend.controller;

import com.fenix.backend.domain.entity.Court;
import com.fenix.backend.domain.enums.CourtStatus;
import com.fenix.backend.dto.request.CourtRequest;
import com.fenix.backend.dto.response.CourtResponse;
import com.fenix.backend.service.CourtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @GetMapping
    public ResponseEntity<List<CourtResponse>> findAll() {
        List<CourtResponse> response = courtService.findAll().stream()
                .map(CourtResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(CourtResponse.from(courtService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CourtResponse> create(@Valid @RequestBody CourtRequest request) {
        Court court = Court.builder()
                .name(request.name())
                .description(request.description())
                .sportType(request.sportType())
                .pricePerHour(request.pricePerHour())
                .build();
        Court created = courtService.create(court);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(CourtResponse.from(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody CourtRequest request) {
        Court data = Court.builder()
                .name(request.name())
                .description(request.description())
                .sportType(request.sportType())
                .pricePerHour(request.pricePerHour())
                .build();
        return ResponseEntity.ok(CourtResponse.from(courtService.update(id, data)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CourtResponse> changeStatus(@PathVariable Long id,
                                                      @RequestBody CourtStatus status) {
        return ResponseEntity.ok(CourtResponse.from(courtService.changeStatus(id, status)));
    }
}
