package com.finanzas_backend_spring.payments_system.repositories;

import com.finanzas_backend_spring.payments_system.models.Trust;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrustRepository extends JpaRepository<Trust,Long> {
    List<Trust> findByAccountId (Long accountId);
}
