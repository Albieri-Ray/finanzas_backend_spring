package com.finanzas_backend_spring.user_system.services.impl;

import com.finanzas_backend_spring.user_system.models.Client;
import com.finanzas_backend_spring.user_system.repositories.ClientRepository;
import com.finanzas_backend_spring.user_system.repositories.UserRepository;
import com.finanzas_backend_spring.user_system.services.ClientService;
import com.finanzas_backend_spring.user_system.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Page<Client> getAllClientsByUser(Long userId, Pageable pageable) {
        return clientRepository.findByUserId(userId, pageable);
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(()-> new NotFoundException("client","id",id));
    }

    @Override
    public Client save(Client client, Long userId) {
        client.setActive(true);
        client.setUser(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("user","id",userId)));
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client existed = clientRepository.findById(id).orElseThrow(()-> new NotFoundException("client","id",id));
        existed.setFirstName(client.getFirstName());
        existed.setLastName(client.getLastName());
        existed.setDni(client.getDni());
        existed.setPhone(client.getPhone());
        return clientRepository.save(existed);
    }

    @Override
    public Client changeState(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(()-> new NotFoundException("user","id",id));
        client.setActive(verify(client.getActive()));
        return clientRepository.save(client);
    }

    private Boolean verify(Boolean active) {
        return !active;
    }
}
