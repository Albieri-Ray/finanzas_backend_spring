package com.finanzas_backend_spring.auth.controller;

import com.finanzas_backend_spring.auth.JwtUtil;
import com.finanzas_backend_spring.auth.resources.JWTResponse;
import com.finanzas_backend_spring.auth.resources.LoginResource;
import com.finanzas_backend_spring.auth.resources.Message;
import com.finanzas_backend_spring.user_system.models.User;
import com.finanzas_backend_spring.user_system.resources.SaveUserResource;
import com.finanzas_backend_spring.user_system.resources.UserResource;
import com.finanzas_backend_spring.user_system.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("api/")
public class AuthController {
    private final UserService userService;

    private final ModelMapper mapper;
    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, ModelMapper mapper, AuthenticationManager manager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.mapper = mapper;
        this.manager = manager;
        this.jwtUtil = jwtUtil;
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
    @PostMapping("login/")
    public ResponseEntity<?> login(@RequestBody LoginResource user){
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);
        return ResponseEntity.ok(new JWTResponse(jwt));
    }
    public UserResource convertToResource(User entity) {
        return mapper.map(entity, UserResource.class);
    }

    public User convertToEntity(SaveUserResource resource) {
        return mapper.map(resource, User.class);
    }
}
