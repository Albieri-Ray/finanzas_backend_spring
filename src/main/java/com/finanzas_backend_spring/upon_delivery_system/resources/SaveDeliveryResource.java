package com.finanzas_backend_spring.upon_delivery_system.resources;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class SaveDeliveryResource {
    @NotNull
    @Size(max = 30)
    private String name;
    private String description;
    @NotNull
    private BigDecimal priceProduct;
    @NotNull
    private BigDecimal priceDelivery;
    @NotNull
    private String frequency;
    @NotNull
    private String homeAddress;
}
