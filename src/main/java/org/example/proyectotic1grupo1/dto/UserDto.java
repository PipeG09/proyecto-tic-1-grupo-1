package org.example.proyectotic1grupo1.dto;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.models.Role;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private String username;
    private String password;


    // Constructor sin argumentos
    public UserDto() {

    }

    // Constructor con argumentos

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}