package com.finanzas_backend_spring.payments_system.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FullLimitException extends RuntimeException{
    public FullLimitException(String price)
    {
        super(String.format("The price %s exceed the limit credit", price));
    }
}
