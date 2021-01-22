package com.finanzas_backend_spring.user_system.services;

import com.finanzas_backend_spring.user_system.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ClientService {
    Page<Client> getAllClients(Pageable pageable);
    Page<Client> getAllClientsByUser(Long userId, Pageable pageable);
    Client getClientById(Long id);
    Client save(Client client, Long userId);
    Client update(Long id, Client client);
    Client changeState(Long id);
}
