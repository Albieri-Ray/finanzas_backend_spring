package com.finanzas_backend_spring.user_system.services;

import com.finanzas_backend_spring.user_system.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();
    List<Client> getAllClientsByUser(Long userId);
    Client getClientById(Long id);
    Client save(Client client, Long userId);
    Client update(Long id, Client client);
}
