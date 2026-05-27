package com.fenix.backend.service;

import com.fenix.backend.domain.entity.Booking;
import com.fenix.backend.domain.entity.Client;
import com.fenix.backend.domain.entity.Court;
import com.fenix.backend.domain.enums.BookingStatus;
import com.fenix.backend.domain.enums.ClientStatus;
import com.fenix.backend.domain.enums.CourtStatus;
import com.fenix.backend.exception.ResourceNotFoundException;
import com.fenix.backend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ClientService clientService;
    private final CourtService courtService;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));
    }

    public List<Booking> findByClient(Long clientId) {
        clientService.findById(clientId);
        return bookingRepository.findAllByClientId(clientId);
    }

    public List<Booking> findByCourt(Long courtId) {
        courtService.findById(courtId);
        return bookingRepository.findAllByCourtId(courtId);
    }

    @Transactional
    public Booking create(Booking booking) {
        if (!booking.getStartTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A reserva deve ser agendada para um horário futuro.");
        }

        if (!booking.getEndTime().isAfter(booking.getStartTime())) {
            throw new IllegalArgumentException("O horário de término deve ser posterior ao de início.");
        }

        Client client = clientService.findById(booking.getClient().getId());
        if (client.getStatus() != ClientStatus.ACTIVE) {
            throw new IllegalStateException("Apenas clientes ativos podem realizar reservas.");
        }

        Court court = courtService.findById(booking.getCourt().getId());
        if (court.getStatus() != CourtStatus.ACTIVE) {
            throw new IllegalStateException("A quadra não está disponível para reservas.");
        }

        boolean conflict = bookingRepository.existsConflict(
                court.getId(), BookingStatus.CONFIRMED, booking.getStartTime(), booking.getEndTime());
        if (conflict) {
            throw new IllegalStateException("Já existe uma reserva confirmada nesse horário para esta quadra.");
        }

        long minutes = Duration.between(booking.getStartTime(), booking.getEndTime()).toMinutes();
        BigDecimal hours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);
        booking.setTotalPrice(court.getPricePerHour().multiply(hours).setScale(2, RoundingMode.HALF_UP));

        booking.setClient(client);
        booking.setCourt(court);
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancel(Long id) {
        Booking booking = findById(id);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Apenas reservas confirmadas podem ser canceladas.");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking complete(Long id) {
        Booking booking = findById(id);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Apenas reservas confirmadas podem ser concluídas.");
        }
        booking.setStatus(BookingStatus.COMPLETED);
        return bookingRepository.save(booking);
    }
}
