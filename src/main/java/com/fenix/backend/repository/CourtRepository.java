package com.fenix.backend.repository;

import com.fenix.backend.domain.entity.Court;
import com.fenix.backend.domain.enums.CourtStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    List<Court> findAllByStatus(CourtStatus status);

    List<Court> findAllBySportType(String sportType);
}
