package com.finanzas_backend_spring.auth.controller;

import com.finanzas_backend_spring.user_system.models.User;
import com.finanzas_backend_spring.user_system.resources.SaveUserResource;
import com.finanzas_backend_spring.user_system.resources.UserResource;
import com.finanzas_backend_spring.user_system.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class AuthController {
    private final UserService userService;

    private final ModelMapper mapper;

    @Autowired
    public AuthController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    public UserResource convertToResource(User entity) {
        return mapper.map(entity, UserResource.class);
    }

    public User convertToEntity(SaveUserResource resource) {
        return mapper.map(resource, User.class);
    }
}
