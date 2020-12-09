package com.finanzas_backend_spring.user_system.controllers;

import com.finanzas_backend_spring.user_system.models.User;
import com.finanzas_backend_spring.user_system.resources.SaveUserResource;
import com.finanzas_backend_spring.user_system.resources.UserResource;
import com.finanzas_backend_spring.user_system.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class UserController {
    private final UserService userService;

    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("users/")
    public ResponseEntity<List<UserResource>> getAllUsers(){
        try
        {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<UserResource> userResources = users.stream().map(this::convertToResource).collect(Collectors.toList());
            return new ResponseEntity<>(userResources, HttpStatus.OK);
        }catch (Exception e){
         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("users/{id}/")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return new ResponseEntity<>(convertToResource(user),HttpStatus.OK);
    }

    @PutMapping("users/{id}/")
    public ResponseEntity<UserResource> UpdateUserById (@PathVariable Long id,@RequestBody SaveUserResource saveUserResource){

        User user = userService.update(id,convertToEntity(saveUserResource));
        return new ResponseEntity<>(convertToResource(user),HttpStatus.OK);
    }

    public UserResource convertToResource(User entity) {
        return mapper.map(entity, UserResource.class);
    }

    public User convertToEntity(SaveUserResource resource) {
        return mapper.map(resource, User.class);
    }
}
