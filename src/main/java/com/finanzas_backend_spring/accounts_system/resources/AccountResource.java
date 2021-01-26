package com.finanzas_backend_spring.accounts_system.resources;

import com.finanzas_backend_spring.accounts_system.models.LineOfCredit;
import lombok.Data;

@Data
public class AccountResource {
    private Long id;
    private String clientName;
    private String maintenanceDescription;
    private LineOfCreditResource lineOfCredit;
}
