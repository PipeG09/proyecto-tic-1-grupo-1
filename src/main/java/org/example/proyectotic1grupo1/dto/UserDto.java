package org.example.proyectotic1grupo1.dto;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.models.Role;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private Long id;
    private String username;
    private String fullname;
    private String password;
    private Boolean isAdmin;
    private List<Long> roleIds;

    // Constructor sin argumentos
    public UserDto() {
        this.isAdmin = false;
    }

    // Constructor con argumentos
    public UserDto(Long id, String username, String fullname, String password, Boolean isAdmin, List<Long> roleIds) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.isAdmin = false;
        this.roleIds = roleIds;
    }

    // Método estático para convertir de User a UserDto
    public static UserDto fromUser(User user) {
        List<Long> roleIds = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        // Imprimir los nombres de los roles del usuario
        user.getRoles().forEach(role -> System.out.println("Rol del usuario: " + role.getName()));

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN") || role.getName().equalsIgnoreCase("ROLE_ADMIN"));

        System.out.println("isAdmin para el usuario " + user.getUsername() + ": " + isAdmin);

        return new UserDto(user.getId(), user.getUsername(), user.getFullname(), user.getPassword(), isAdmin, roleIds);
    }



    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", username=" + username + ", fullname=" + fullname
                + ", isAdmin=" + isAdmin + ", roleIds=" + roleIds + "]";
    }
}
