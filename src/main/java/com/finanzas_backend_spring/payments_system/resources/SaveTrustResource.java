package com.finanzas_backend_spring.payments_system.resources;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SaveTrustResource {
    @NotNull
    private BigDecimal priceProduct;
    @NotNull
    private String products;
}
