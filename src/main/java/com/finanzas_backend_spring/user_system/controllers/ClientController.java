package com.finanzas_backend_spring.user_system.controllers;

import com.finanzas_backend_spring.user_system.models.Client;

import com.finanzas_backend_spring.user_system.resources.ClientResource;
import com.finanzas_backend_spring.user_system.resources.SaveClientResource;

import com.finanzas_backend_spring.user_system.services.ClientService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class ClientController {
    private final ClientService clientService;
    private final ModelMapper mapper;

    @Autowired
    public ClientController(ClientService clientService, ModelMapper mapper) {
        this.clientService = clientService;
        this.mapper = mapper;
    }

    @GetMapping("clients/")
    public ResponseEntity<Page<ClientResource>> getAllClients(Pageable pageable){
        try
        {
            Page<Client> clients = clientService.getAllClients(pageable);
            if(clients.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ClientResource> clientResources = clients.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
            Page<ClientResource> resources = new PageImpl<>(clientResources, pageable, clientResources.size());
            return new ResponseEntity<>(resources,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("users/{id}/clients/")
    public ResponseEntity<Page<ClientResource>> getAllClientsByUser(@PathVariable Long id,Pageable pageable){
        try
        {
            Page<Client> clients = clientService.getAllClientsByUser(id, pageable);
            if(clients.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ClientResource> clientResources = clients.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
            Page<ClientResource> resources = new PageImpl<>(clientResources, pageable, clientResources.size());
            return new ResponseEntity<>(resources,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("users/{userId}/clients/")
    public ResponseEntity<ClientResource> create(@RequestBody SaveClientResource saveClientResource, @PathVariable Long userId)
    {
        Client client = clientService.save(convertToEntity(saveClientResource),userId);
        return new ResponseEntity<>(convertToResource(client), HttpStatus.OK);
    }

    @GetMapping("clients/{id}/")
    public ResponseEntity<ClientResource> getClientById(@PathVariable Long id){
        Client client = clientService.getClientById(id);
        return new ResponseEntity<>(convertToResource(client), HttpStatus.OK);
    }
    @PutMapping("clients/{id}")
    public ResponseEntity<ClientResource> UpdateClientById(@PathVariable Long id, @RequestBody SaveClientResource saveClientResource){
        Client client = clientService.update(id,convertToEntity(saveClientResource));
        return new ResponseEntity<>(convertToResource(client), HttpStatus.OK);
    }

    @GetMapping("clients/{id}/active")
    public ResponseEntity<ClientResource> changeState(@PathVariable Long id){
        Client existed = clientService.changeState(id);
        return new ResponseEntity<>(convertToResource(existed), HttpStatus.OK);
    }

    @PostConstruct
    public void init(){
        mapper.addMappings(new PropertyMap<Client, ClientResource>() {
            @Override
            protected void configure() {
                map().setUserEmail(source.getUser().getEmail());
            }
        });
    }

    public ClientResource convertToResource(Client entity) {
        return mapper.map(entity, ClientResource.class);
    }

    public Client convertToEntity(SaveClientResource resource) {
        return mapper.map(resource, Client.class);
    }
}