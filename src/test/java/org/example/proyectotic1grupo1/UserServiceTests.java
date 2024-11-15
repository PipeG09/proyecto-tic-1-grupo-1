package org.example.proyectotic1grupo1;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.repositories.UserRepository;
import org.example.proyectotic1grupo1.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Esto inicializa los mocks
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(user);

        User foundUser = userService.findByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }


    @Test
    void testFindById() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        verify(userRepository, times(1)).findById(userId);
    }
}

