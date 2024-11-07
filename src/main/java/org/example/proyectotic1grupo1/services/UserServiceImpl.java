package org.example.proyectotic1grupo1.services;

import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;


    private UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) { // Añadir RoleRepository aquí
        super();
        this.userRepository = userRepository;

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
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
    public void updateUserProfile(String username, User userDto) {
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
    public User findById(Long id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public boolean validate(User user, String password) {
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(password, user.getPassword());
    }


}
