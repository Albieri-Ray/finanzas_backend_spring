package com.finanzas_backend_spring.auth.resources;

import lombok.Data;

@Data
public class Message {
    private String message;

    public Message(String message) {
        this.message = message;
    }
}
