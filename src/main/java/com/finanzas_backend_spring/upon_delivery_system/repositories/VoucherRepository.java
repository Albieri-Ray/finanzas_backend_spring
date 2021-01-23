package com.finanzas_backend_spring.upon_delivery_system.repositories;

import com.finanzas_backend_spring.upon_delivery_system.models.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
    Page<Voucher> findByDeliveryId (Long deliveryId, Pageable pageable);
}
