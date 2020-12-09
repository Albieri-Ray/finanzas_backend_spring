package com.finanzas_backend_spring.user_system.resources;

import lombok.Data;

@Data
public class UserResource {
    private Long id;
    private String email;
    private String name;
}
