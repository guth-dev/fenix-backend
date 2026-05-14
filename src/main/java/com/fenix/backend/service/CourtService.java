package com.fenix.backend.service;

import com.fenix.backend.domain.entity.Court;
import com.fenix.backend.domain.enums.CourtStatus;
import com.fenix.backend.exception.ResourceNotFoundException;
import com.fenix.backend.repository.CourtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final CourtRepository courtRepository;

    public List<Court> findAll() {
        return courtRepository.findAll();
    }

    public Court findById(Long id) {
        return courtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Court", id));
    }

    @Transactional
    public Court create(Court court) {
        court.setStatus(CourtStatus.ACTIVE);
        return courtRepository.save(court);
    }

    @Transactional
    public Court update(Long id, Court data) {
        Court court = findById(id);
        court.setName(data.getName());
        court.setDescription(data.getDescription());
        court.setSportType(data.getSportType());
        court.setPricePerHour(data.getPricePerHour());
        return courtRepository.save(court);
    }

    @Transactional
    public Court changeStatus(Long id, CourtStatus status) {
        Court court = findById(id);
        court.setStatus(status);
        return courtRepository.save(court);
    }
}
