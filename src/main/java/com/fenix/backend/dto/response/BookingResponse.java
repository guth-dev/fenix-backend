package com.fenix.backend.dto.response;

import com.fenix.backend.domain.entity.Booking;
import com.fenix.backend.domain.enums.BookingStatus;
import com.fenix.backend.domain.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Long clientId,
        String clientName,
        Long courtId,
        String courtName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        BigDecimal totalPrice,
        PaymentMethod paymentMethod,
        BookingStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getClient().getId(),
                booking.getClient().getName(),
                booking.getCourt().getId(),
                booking.getCourt().getName(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getTotalPrice(),
                booking.getPaymentMethod(),
                booking.getStatus(),
                booking.getCreatedAt(),
                booking.getUpdatedAt()
        );
    }
}
