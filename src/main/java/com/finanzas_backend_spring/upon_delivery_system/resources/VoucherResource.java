package com.finanzas_backend_spring.upon_delivery_system.resources;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VoucherResource {
    private Long id;
    private String frequency;
    private BigDecimal total;
    private LocalDate registerDate;
    private String deliveryName;
    private String description;
}
