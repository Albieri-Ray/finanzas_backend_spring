package com.finanzas_backend_spring.user_system.services;

import com.finanzas_backend_spring.user_system.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User save(User user);
    User update(Long id, User user);
}
