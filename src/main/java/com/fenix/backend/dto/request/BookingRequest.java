package com.fenix.backend.dto.request;

import com.fenix.backend.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookingRequest(
        @NotNull Long clientId,
        @NotNull Long courtId,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        @NotNull PaymentMethod paymentMethod
) {}
