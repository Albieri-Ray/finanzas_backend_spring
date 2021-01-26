package com.finanzas_backend_spring.accounts_system.resources;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SaveLineOfCreditResource {
    @NotNull
    private String rateType;
    @NotNull
    private BigDecimal credit;
    @NotNull
    private String typeOfCurrency;
    @NotNull
    private String period;
    @NotNull
    private BigDecimal rateValue;
}
