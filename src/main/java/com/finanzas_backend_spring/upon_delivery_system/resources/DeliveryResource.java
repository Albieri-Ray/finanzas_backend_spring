package com.finanzas_backend_spring.upon_delivery_system.resources;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DeliveryResource {
    private Long id;
    private String name;
    private String description;
    private BigDecimal priceProduct;
    private BigDecimal priceDelivery;
    private LocalDate registerDate;
    private String frequency;
    private String homeAddress;
    private String clientName;
}
