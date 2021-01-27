package com.finanzas_backend_spring.payments_system.services;

import com.finanzas_backend_spring.payments_system.models.Receipt;

import java.util.List;

public interface ReceiptService {
    List<Receipt> getAllReceiptByClient(Long clientId);
    Receipt getReceiptById (Long id);
    Receipt save (Receipt receipt, Long accountId);
    void delete(Long id);
}
