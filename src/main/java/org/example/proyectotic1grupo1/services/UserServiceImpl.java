package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.Role;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.repositories.RoleRepository;
import org.example.proyectotic1grupo1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    public boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN") || role.getName().equalsIgnoreCase("ROLE_ADMIN"));

    }





    @Override
     public User save(User user) {
        if (userRepository.findByUsername(user.getUsername())!=null) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

        // Si el administrador proporciona una nueva contraseña, la ciframos y la actualizamos
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Actualizar roles
        if (userDto.getRoleIds() != null && !userDto.getRoleIds().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(userDto.getRoleIds());
            user.setRoles(new HashSet<>(roles));
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));

        userRepository.delete(user);
    }


    @Override
    public User findById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public boolean validate(User user, String password) {
        if (user == null) {
            return false;
        }
        return passwordEncoder.encode(password).equals(user.getPassword());
    }
}
