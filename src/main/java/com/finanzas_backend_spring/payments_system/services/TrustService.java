package com.finanzas_backend_spring.payments_system.services;

import com.finanzas_backend_spring.payments_system.models.Trust;

import java.util.List;

public interface TrustService {
    List<Trust> getAllTrusts ();
    List<Trust> getAllTrustByAccount(Long accountId);
    Trust getTrustById (Long id);
    Trust save (Trust trust, Long accountId);
    Trust update(Long id, Trust trust);
    void delete(Long id);
}
