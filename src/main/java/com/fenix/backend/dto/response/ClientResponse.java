package com.fenix.backend.dto.response;

import com.fenix.backend.domain.entity.Client;
import com.fenix.backend.domain.enums.ClientStatus;

import java.time.LocalDateTime;

public record ClientResponse(
        Long id,
        String name,
        String email,
        String phone,
        String cpf,
        ClientStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ClientResponse from(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getCpf(),
                client.getStatus(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}
