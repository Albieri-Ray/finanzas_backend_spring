package com.finanzas_backend_spring.accounts_system.resources;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class SaveMaintenanceResource {
    @NotNull
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String frequency;
}
