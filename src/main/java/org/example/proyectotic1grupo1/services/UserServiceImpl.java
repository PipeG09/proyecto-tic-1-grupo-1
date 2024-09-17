package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.Role;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.repositories.RoleRepository;
import org.example.proyectotic1grupo1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private RoleRepository roleRepository; // Agrega RoleRepository

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) { // Añadir RoleRepository aquí
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository; // Inicializa RoleRepository
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
                userDto.getFullname());

        // Asignación del rol USER por defecto
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(userRole);

        // Si es un admin, asignar el rol ADMIN
        if (userDto.isAdmin()) { // Supongo que en el DTO agregas una propiedad isAdmin
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
            user.getRoles().add(adminRole);
        }

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void updateUserProfile(String username, UserDto userDto) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Actualizamos solo el fullname
        user.setFullname(userDto.getFullname());

        // No actualizamos el username ni el password

        userRepository.save(user);
    }



    @Override
    public void updateUserById(Long id, UserDto userDto) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));

        user.setFullname(userDto.getFullname());
        user.setUsername(userDto.getUsername());

        // Si el administrador proporciona una nueva contraseña, la ciframos y la actualizamos
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) throws Exception {
        userRepository.deleteById(id);
    }
}
