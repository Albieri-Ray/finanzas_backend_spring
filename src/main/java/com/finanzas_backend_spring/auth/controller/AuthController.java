package com.finanzas_backend_spring.auth.controller;

import com.finanzas_backend_spring.auth.resources.Message;
import com.finanzas_backend_spring.user_system.models.User;
import com.finanzas_backend_spring.user_system.resources.SaveUserResource;
import com.finanzas_backend_spring.user_system.resources.UserResource;
import com.finanzas_backend_spring.user_system.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("register/")
    public ResponseEntity<?> register(@RequestBody SaveUserResource saveUserResource){
        if (userService.existsByEmail(saveUserResource.getEmail())){
            return ResponseEntity.badRequest().body(new Message("el email esta ocupado"));
        }
        try{
            User user = userService.save(convertToEntity(saveUserResource));
            return new ResponseEntity<>(convertToResource(user),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserResource convertToResource(User entity) {
        return mapper.map(entity, UserResource.class);
    }

    public User convertToEntity(SaveUserResource resource) {
        return mapper.map(resource, User.class);
    }
}
