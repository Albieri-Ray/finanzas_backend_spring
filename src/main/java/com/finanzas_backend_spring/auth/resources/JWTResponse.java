package com.finanzas_backend_spring.auth.resources;
import lombok.Data;
@Data

public class JWTResponse {
    private String token;
    public JWTResponse(String token) {
        this.token = token;
    }
}