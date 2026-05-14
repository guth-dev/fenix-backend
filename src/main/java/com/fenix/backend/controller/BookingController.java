package com.fenix.backend.controller;

import com.fenix.backend.domain.entity.Booking;
import com.fenix.backend.domain.entity.Client;
import com.fenix.backend.domain.entity.Court;
import com.fenix.backend.dto.request.BookingRequest;
import com.fenix.backend.dto.response.BookingResponse;
import com.fenix.backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> findAll() {
        List<BookingResponse> response = bookingService.findAll().stream()
                .map(BookingResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(BookingResponse.from(bookingService.findById(id)));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BookingResponse>> findByClient(@PathVariable Long clientId) {
        List<BookingResponse> response = bookingService.findByClient(clientId).stream()
                .map(BookingResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/court/{courtId}")
    public ResponseEntity<List<BookingResponse>> findByCourt(@PathVariable Long courtId) {
        List<BookingResponse> response = bookingService.findByCourt(courtId).stream()
                .map(BookingResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingRequest request) {
        Booking booking = Booking.builder()
                .client(Client.builder().id(request.clientId()).build())
                .court(Court.builder().id(request.courtId()).build())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .paymentMethod(request.paymentMethod())
                .build();
        Booking created = bookingService.create(booking);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(BookingResponse.from(created));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(BookingResponse.from(bookingService.cancel(id)));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<BookingResponse> complete(@PathVariable Long id) {
        return ResponseEntity.ok(BookingResponse.from(bookingService.complete(id)));
    }
}
