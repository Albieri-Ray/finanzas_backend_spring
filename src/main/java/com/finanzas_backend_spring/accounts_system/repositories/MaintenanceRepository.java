package com.finanzas_backend_spring.accounts_system.repositories;

import com.finanzas_backend_spring.accounts_system.models.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance,Long> {
    List<Maintenance> findByUserId(Long userId);
}
