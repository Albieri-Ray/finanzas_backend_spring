package com.finanzas_backend_spring.user_system.resources;

import lombok.Data;

@Data
public class ClientResource {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String dni;
    private Boolean active;
    private String userEmail;
}