package com.finanzas_backend_spring.payments_system.resources;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TrustResource {
    private Long id;
    private BigDecimal priceProduct;
    private LocalDate registerDate;
    private String products;
}
