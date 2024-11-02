package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.dto.UserDto;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    User save(User user);

    void updateUserProfile(String username, User userDto) throws Exception;

    User findById(Long id) throws Exception;

    List<User> findAll();

    boolean isAdmin(User user);

    boolean validate(User user, String password);



}