package com.finanzas_backend_spring.upon_delivery_system.services;

import com.finanzas_backend_spring.upon_delivery_system.models.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VoucherService {
    Page<Voucher> getAllVouchers (Pageable pageable);
    Page<Voucher> getAllVouchersByDelivery(Long deliveryId, Pageable pageable);
    Voucher getVoucherById(Long id);
    Voucher save(Long deliveryId);
    void delete(Long id);
}
