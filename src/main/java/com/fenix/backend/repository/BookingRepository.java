package com.fenix.backend.repository;

import com.fenix.backend.domain.entity.Booking;
import com.fenix.backend.domain.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByClientId(Long clientId);

    List<Booking> findAllByCourtId(Long courtId);

    List<Booking> findAllByStatus(BookingStatus status);

    @Query("""
            SELECT COUNT(b) > 0 FROM Booking b
            WHERE b.court.id = :courtId
              AND b.status = :status
              AND b.startTime < :endTime
              AND b.endTime > :startTime
            """)
    boolean existsConflict(@Param("courtId") Long courtId,
                           @Param("status") BookingStatus status,
                           @Param("startTime") LocalDateTime startTime,
                           @Param("endTime") LocalDateTime endTime);
}
