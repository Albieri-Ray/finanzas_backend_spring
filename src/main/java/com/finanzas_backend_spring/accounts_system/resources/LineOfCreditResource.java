package com.finanzas_backend_spring.accounts_system.resources;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LineOfCreditResource {
    private Long id;
    private String rateType;
    private BigDecimal credit;
    private String typeOfCurrency;
    private LocalDate registerDate;
    private String period;
    private BigDecimal rateValue;
}
