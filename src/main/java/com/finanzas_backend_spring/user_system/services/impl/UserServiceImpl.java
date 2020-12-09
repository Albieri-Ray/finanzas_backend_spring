package com.finanzas_backend_spring.user_system.services.impl;

import com.finanzas_backend_spring.user_system.models.User;
import com.finanzas_backend_spring.user_system.repositories.UserRepository;
import com.finanzas_backend_spring.user_system.services.NotFoundException;
import com.finanzas_backend_spring.user_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("user","id",id));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User existed = userRepository.findById(id).orElseThrow(()-> new NotFoundException("user","id",id));
        existed.setName(user.getName());
        existed.setEmail(user.getEmail());
        existed.setPassword(user.getPassword());
        return userRepository.save(existed);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
