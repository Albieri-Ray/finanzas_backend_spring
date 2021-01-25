package com.finanzas_backend_spring.accounts_system.controllers;

import com.finanzas_backend_spring.accounts_system.models.Maintenance;
import com.finanzas_backend_spring.accounts_system.resources.MaintenanceResource;
import com.finanzas_backend_spring.accounts_system.resources.SaveMaintenanceResource;
import com.finanzas_backend_spring.accounts_system.services.MaintenanceService;
import com.finanzas_backend_spring.auth.resources.Message;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;
    private final ModelMapper mapper;

    public MaintenanceController(MaintenanceService maintenanceService, ModelMapper mapper) {
        this.maintenanceService = maintenanceService;
        this.mapper = mapper;
    }

    @GetMapping("maintenances/")
    public ResponseEntity<List<MaintenanceResource>> getAllMaintenances(){
        try
        {
            List<Maintenance> maintenances = maintenanceService.getAllMaintenances();
            if(maintenances.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<MaintenanceResource> maintenanceResources = maintenances.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(maintenanceResources, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("users/{id}/maintenances/")
    public ResponseEntity<List<MaintenanceResource>> getAllMaintenancesByUser(@PathVariable Long id){
        try
        {
            List<Maintenance> maintenances = maintenanceService.getAllMaintenancesByUser(id);
            if(maintenances.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<MaintenanceResource> maintenanceResources = maintenances.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(maintenanceResources,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("users/{userId}/maintenances/")
    public ResponseEntity<MaintenanceResource> create(@RequestBody SaveMaintenanceResource saveMaintenanceResource, @PathVariable Long userId)
    {
        Maintenance maintenance = maintenanceService.save(convertToEntity(saveMaintenanceResource),userId);
        return new ResponseEntity<>(convertToResource(maintenance), HttpStatus.OK);
    }

    @GetMapping("maintenances/{id}/")
    public ResponseEntity<MaintenanceResource> getMaintenanceById(@PathVariable Long id){
        Maintenance maintenance = maintenanceService.getMaintenanceById(id);
        return new ResponseEntity<>(convertToResource(maintenance), HttpStatus.OK);
    }
    @PutMapping("maintenances/{id}/")
    public ResponseEntity<MaintenanceResource> UpdateMaintenanceById(@PathVariable Long id, @RequestBody SaveMaintenanceResource saveMaintenanceResource){
        Maintenance maintenance = maintenanceService.update(id,convertToEntity(saveMaintenanceResource));
        return new ResponseEntity<>(convertToResource(maintenance), HttpStatus.OK);
    }

    @DeleteMapping("maintenances/{id}/")
    public ResponseEntity<Message> delete (@PathVariable Long id){
        maintenanceService.delete(id);
        Message message = new Message("Delete successful");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @PostConstruct
    public void init(){
        mapper.addMappings(new PropertyMap<Maintenance, MaintenanceResource>() {
            @Override
            protected void configure() {
                map().setUserName(source.getUser().getName());
            }
        });
    }

    public MaintenanceResource convertToResource(Maintenance entity) {
        return mapper.map(entity, MaintenanceResource.class);
    }

    public Maintenance convertToEntity(SaveMaintenanceResource resource) {
        return mapper.map(resource, Maintenance.class);
    }

}
