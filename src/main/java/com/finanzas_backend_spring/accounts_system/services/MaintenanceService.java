package com.finanzas_backend_spring.accounts_system.services;


import com.finanzas_backend_spring.accounts_system.models.Maintenance;

import java.util.List;

public interface MaintenanceService {
    List<Maintenance> getAllMaintenances();
    List<Maintenance> getAllMaintenancesByUser(Long userId);
    Maintenance getMaintenanceById(Long id);
    Maintenance save(Maintenance maintenance,Long userId);
    Maintenance update(Long id, Maintenance maintenance);
    void delete(Long id);
}
