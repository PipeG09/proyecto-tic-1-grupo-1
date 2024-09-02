package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.dto.UserDto;

public interface UserService {
    User findByUsername(String username);

    User save(UserDto userDto);

}