package com.fenix.backend.repository;

import com.fenix.backend.domain.entity.Client;
import com.fenix.backend.domain.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    Optional<Client> findByCpf(String cpf);

    List<Client> findAllByStatus(ClientStatus status);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
