package com.fenix.backend.dto.response;

import com.fenix.backend.domain.entity.Court;
import com.fenix.backend.domain.enums.CourtStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CourtResponse(
        Long id,
        String name,
        String description,
        String sportType,
        BigDecimal pricePerHour,
        CourtStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CourtResponse from(Court court) {
        return new CourtResponse(
                court.getId(),
                court.getName(),
                court.getDescription(),
                court.getSportType(),
                court.getPricePerHour(),
                court.getStatus(),
                court.getCreatedAt(),
                court.getUpdatedAt()
        );
    }
}
