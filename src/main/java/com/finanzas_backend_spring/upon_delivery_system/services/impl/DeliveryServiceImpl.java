package com.finanzas_backend_spring.upon_delivery_system.services.impl;

import com.finanzas_backend_spring.upon_delivery_system.models.Delivery;
import com.finanzas_backend_spring.upon_delivery_system.repositories.DeliveryRepository;
import com.finanzas_backend_spring.upon_delivery_system.services.DeliveryService;
import com.finanzas_backend_spring.user_system.repositories.ClientRepository;
import com.finanzas_backend_spring.user_system.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final ClientRepository clientRepository;
    private final LocalDate today = LocalDate.now();
    @Autowired
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, ClientRepository clientRepository) {
        this.deliveryRepository = deliveryRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Page<Delivery> getAllDeliveries(Pageable pageable) {
        return deliveryRepository.findAll(pageable);
    }

    @Override
    public Page<Delivery> getAllDeliveryByClient(Long clientId, Pageable pageable) {
        return deliveryRepository.findByClientId(clientId, pageable);
    }

    @Override
    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id).orElseThrow(()->new NotFoundException("delivery","id",id));
    }

    @Override
    public Delivery save(Delivery delivery, Long clientId) {
        //Date Fecha = today;
        //delivery.setRegisterDate(Fecha);
        delivery.setClient(clientRepository.findById(clientId).orElseThrow(()->new NotFoundException("client","id", clientId)));
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery update(Long id, Delivery delivery) {
        Delivery existed = deliveryRepository.findById(id).orElseThrow(()->new NotFoundException("delivery","id",id));
        existed.setName(delivery.getName());
        existed.setDescription(delivery.getDescription());
        existed.setPriceProduct(delivery.getPriceProduct());
        existed.setPriceDelivery(delivery.getPriceDelivery());
        existed.setFrequency(delivery.getFrequency());
        existed.setHomeAddress(delivery.getHomeAddress());
        return deliveryRepository.save(existed);
    }
}
