package com.finanzas_backend_spring.accounts_system.resources;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaintenanceResource {
    private Long id;
    private String description;
    private BigDecimal price;
    private String frequency;
    private String userName;
}
