package com.finanzas_backend_spring.auth.resources;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginResource {
    @NotNull
    @Size(max = 25)
    private String email;
    @NotNull
    @Size(max = 15)
    private String password;
}
