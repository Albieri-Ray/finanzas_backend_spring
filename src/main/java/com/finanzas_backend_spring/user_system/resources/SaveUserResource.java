package com.finanzas_backend_spring.user_system.resources;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaveUserResource {
    @NotNull
    @Size(max = 25)
    private String email;
    @NotNull
    @Size(max = 10)
    private String name;
    @NotNull
    @Size(max = 15)
    private String password;
}
