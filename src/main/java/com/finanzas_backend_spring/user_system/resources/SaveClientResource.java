package com.finanzas_backend_spring.user_system.resources;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveClientResource {
    @NotNull
    @Size(max = 30)
    private String firstName;
    @NotNull
    @Size(max = 30)
    private String lastName;
    @NotNull
    @Size(max = 10)
    private String phone;
    @NotNull
    @Size(max = 8)
    private String dni;
}
