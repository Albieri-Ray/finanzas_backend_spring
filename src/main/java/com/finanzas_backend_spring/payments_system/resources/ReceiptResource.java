package com.finanzas_backend_spring.payments_system.resources;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReceiptResource {
    private Long id;
    private BigDecimal interest;
    private Integer quantityOfDays;
    private BigDecimal maintenanceCost;
    private BigDecimal creditConsumed;
    private BigDecimal total;
    private LocalDate registerDate;
}
