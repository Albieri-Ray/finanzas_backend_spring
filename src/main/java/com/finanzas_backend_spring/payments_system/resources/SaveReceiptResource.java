package com.finanzas_backend_spring.payments_system.resources;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SaveReceiptResource {
    private LocalDate registerDate;
}
