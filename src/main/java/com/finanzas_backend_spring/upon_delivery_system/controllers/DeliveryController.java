package com.finanzas_backend_spring.upon_delivery_system.controllers;

import com.finanzas_backend_spring.upon_delivery_system.models.Delivery;
import com.finanzas_backend_spring.upon_delivery_system.resources.DeliveryResource;
import com.finanzas_backend_spring.upon_delivery_system.resources.SaveDeliveryResource;
import com.finanzas_backend_spring.upon_delivery_system.services.DeliveryService;
import com.finanzas_backend_spring.user_system.models.Client;
import com.finanzas_backend_spring.user_system.resources.ClientResource;
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
public class DeliveryController {
    private  final DeliveryService deliveryService;
    private final ModelMapper mapper;

    @Autowired
    public DeliveryController(DeliveryService deliveryService, ModelMapper mapper) {
        this.deliveryService = deliveryService;
        this.mapper = mapper;
    }

    @GetMapping("deliveries/")
    public ResponseEntity<Page<DeliveryResource>> getAllDeliveries (Pageable pageable){
        try
        {
            Page<Delivery> deliveries = deliveryService.getAllDeliveries(pageable);
            if(deliveries.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<DeliveryResource> deliveryResources = deliveries.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
            Page<DeliveryResource> resources = new PageImpl<>(deliveryResources, pageable, deliveryResources.size());
            return new ResponseEntity<>(resources,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("clients/{id}/deliveries/")
    public ResponseEntity<Page<DeliveryResource>> getAllDeliveriesByClient(@PathVariable Long id, Pageable pageable){
        try {
            Page<Delivery> deliveries = deliveryService.getAllDeliveriesByClient(id, pageable);
            if (deliveries.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<DeliveryResource> deliveryResources = deliveries.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
            Page<DeliveryResource> resources = new PageImpl<>(deliveryResources, pageable, deliveryResources.size());
            return new ResponseEntity<>(resources, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("clients/{clientId}/deliveries/")
    public ResponseEntity<DeliveryResource> create (@RequestBody SaveDeliveryResource saveDeliveryResource, @PathVariable Long clientId)
    {
        Delivery delivery = deliveryService.save(convertToEntity(saveDeliveryResource), clientId);
        return new ResponseEntity<>(convertToResource(delivery),HttpStatus.OK);
    }

    @GetMapping("deliveries/{id}/")
    public ResponseEntity<DeliveryResource> getDeliveryById (@PathVariable Long id){
        Delivery delivery = deliveryService.getDeliveryById(id);
        return new ResponseEntity<>(convertToResource(delivery),HttpStatus.OK);
    }

    @PutMapping("deliveries/{id}")
    public ResponseEntity<DeliveryResource> UpdateDeliveryById(@PathVariable Long id, @RequestBody SaveDeliveryResource saveDeliveryResource){
        Delivery delivery = deliveryService.update(id,convertToEntity(saveDeliveryResource));
        return new ResponseEntity<>(convertToResource(delivery), HttpStatus.OK);
    }

    @PostConstruct
    public void init(){
        mapper.addMappings(new PropertyMap<Delivery, DeliveryResource>() {
            @Override
            protected void configure() {
                map().setClientName(source.getClient().getFirstName());
            }
        });
    }

    public DeliveryResource convertToResource(Delivery entity) {
        return mapper.map(entity, DeliveryResource.class);
    }

    public Delivery convertToEntity(SaveDeliveryResource resource) {
        return mapper.map(resource, Delivery.class);
    }
}
