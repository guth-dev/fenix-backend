package com.fenix.backend.service;

import com.fenix.backend.domain.entity.Client;
import com.fenix.backend.domain.enums.ClientStatus;
import com.fenix.backend.exception.ResourceNotFoundException;
import com.fenix.backend.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Transactional
    public Client create(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + client.getEmail());
        }
        if (clientRepository.existsByCpf(client.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + client.getCpf());
        }
        client.setStatus(ClientStatus.ACTIVE);
        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Long id, Client data) {
        Client client = findById(id);

        if (!client.getEmail().equals(data.getEmail()) && clientRepository.existsByEmail(data.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + data.getEmail());
        }
        if (!client.getCpf().equals(data.getCpf()) && clientRepository.existsByCpf(data.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + data.getCpf());
        }

        client.setName(data.getName());
        client.setEmail(data.getEmail());
        client.setPhone(data.getPhone());
        client.setCpf(data.getCpf());
        return clientRepository.save(client);
    }

    @Transactional
    public Client changeStatus(Long id, ClientStatus status) {
        Client client = findById(id);
        client.setStatus(status);
        return clientRepository.save(client);
    }
}
