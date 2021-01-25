package com.finanzas_backend_spring.accounts_system.services.impl;

import com.finanzas_backend_spring.accounts_system.models.Maintenance;
import com.finanzas_backend_spring.accounts_system.repositories.MaintenanceRepository;
import com.finanzas_backend_spring.accounts_system.services.MaintenanceService;
import com.finanzas_backend_spring.user_system.repositories.UserRepository;
import com.finanzas_backend_spring.user_system.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final UserRepository userRepository;

    @Autowired
    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository, UserRepository userRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    public List<Maintenance> getAllMaintenancesByUser(Long userId) {
        return maintenanceRepository.findByUserId(userId);
    }

    @Override
    public Maintenance getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id).orElseThrow(()-> new NotFoundException("maintenance","id",id));
    }

    @Override
    public Maintenance save(Maintenance maintenance, Long userId) {
        maintenance.setUser(userRepository.findById(userId).orElseThrow(()-> new NotFoundException("user","id",userId)));
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        Maintenance existed = maintenanceRepository.findById(id).orElseThrow(()-> new NotFoundException("maintenance","id",id));
        existed.setDescription(maintenance.getDescription());
        existed.setPrice(maintenance.getPrice());
        existed.setFrequency(maintenance.getFrequency());
        return maintenanceRepository.save(existed);
    }

    @Override
    public void delete(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id).orElseThrow(()-> new NotFoundException("maintenance","id",id));
        maintenanceRepository.delete(maintenance);
    }
}
