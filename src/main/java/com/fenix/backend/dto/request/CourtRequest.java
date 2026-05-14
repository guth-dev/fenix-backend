package com.fenix.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CourtRequest(
        @NotBlank String name,
        String description,
        @NotBlank String sportType,
        @NotNull @Positive BigDecimal pricePerHour
) {}
