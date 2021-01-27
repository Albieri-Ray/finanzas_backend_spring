package com.finanzas_backend_spring.payments_system.controllers;

import com.finanzas_backend_spring.auth.resources.Message;
import com.finanzas_backend_spring.payments_system.models.Trust;
import com.finanzas_backend_spring.payments_system.resources.SaveTrustResource;
import com.finanzas_backend_spring.payments_system.resources.TrustResource;
import com.finanzas_backend_spring.payments_system.services.TrustService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class TrustController {
    private final TrustService trustService;
    private final ModelMapper mapper;

    @Autowired
    public TrustController(TrustService trustService, ModelMapper mapper) {
        this.trustService = trustService;
        this.mapper = mapper;
    }


    @GetMapping("trusts/")
    public ResponseEntity<List<TrustResource>> getAllTrusts (){
        try
        {
            List<Trust> trusts = trustService.getAllTrusts();
            if(trusts.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<TrustResource> trustResources = trusts.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(trustResources,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("accounts/{id}/trusts/")
    public ResponseEntity<List<TrustResource>> getAllTrustsByAccount(@PathVariable Long id){
        try {
            List<Trust> trusts = trustService.getAllTrustByAccount(id);
            if (trusts.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<TrustResource> trustResources = trusts.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(trustResources, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("accounts/{accountId}/trusts/")
    public ResponseEntity<TrustResource> create (@RequestBody SaveTrustResource saveTrustResource, @PathVariable Long accountId)
    {
        Trust trust = trustService.save(convertToEntity(saveTrustResource), accountId);
        return new ResponseEntity<>(convertToResource(trust),HttpStatus.OK);
    }

    @GetMapping("trust/{id}/")
    public ResponseEntity<TrustResource> getTrustById (@PathVariable Long id){
        Trust trust = trustService.getTrustById(id);
        return new ResponseEntity<>(convertToResource(trust),HttpStatus.OK);
    }

    @PutMapping("trust/{id}/")
    public ResponseEntity<TrustResource> UpdateTrustById(@PathVariable Long id, @RequestBody SaveTrustResource saveTrustResource){
        Trust trust = trustService.update(id,convertToEntity(saveTrustResource));
        return new ResponseEntity<>(convertToResource(trust), HttpStatus.OK);
    }
    @DeleteMapping("trust/{id}/")
    public ResponseEntity<Message> delete (@PathVariable Long id){
        trustService.delete(id);
        Message message = new Message("Delete successful");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @DeleteMapping("accounts/{accountId}/trusts")
    public ResponseEntity<Message> deleteAllByAccount (@PathVariable Long accountId){
        trustService.deleteAllByAccount(accountId);
        Message message = new Message("Delete All By Account");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    public TrustResource convertToResource(Trust entity) {
        return mapper.map(entity, TrustResource.class);
    }

    public Trust convertToEntity(SaveTrustResource resource) {
        return mapper.map(resource, Trust.class);
    }
}
