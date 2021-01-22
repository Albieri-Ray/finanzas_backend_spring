package com.finanzas_backend_spring.upon_delivery_system.repositories;

import com.finanzas_backend_spring.upon_delivery_system.models.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    Page<Delivery> findByClientId (Long clientId, Pageable pageable);
}
