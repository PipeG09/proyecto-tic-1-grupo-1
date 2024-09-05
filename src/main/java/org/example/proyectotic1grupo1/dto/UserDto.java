package org.example.proyectotic1grupo1.dto;

public class UserDto {

    private String username;
    private String password;
    private String fullname;
    private Boolean isAdmin; // Asegúrate de tener este campo

    // Actualiza el constructor para incluir isAdmin
    public UserDto(String username, String password, String fullname) {
        super();
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.isAdmin = false; // Asigna isAdmin
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // Métodos get y set para isAdmin
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // Actualiza el toString para incluir isAdmin
    @Override
    public String toString() {
        return "UserDto [username=" + username + ", password=" + password + ", fullname=" + fullname + ", isAdmin=" + isAdmin + "]";
    }
}
