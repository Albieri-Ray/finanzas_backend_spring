package com.finanzas_backend_spring.upon_delivery_system.services;

import com.finanzas_backend_spring.upon_delivery_system.models.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {
    Page<Delivery> getAllDeliveries (Pageable pageable);
    Page<Delivery> getAllDeliveryByClient(Long clientId, Pageable pageable);
    Delivery getDeliveryById(Long id);
    Delivery save(Delivery delivery, Long clientId);
    Delivery update(Long id, Delivery delivery);
}
