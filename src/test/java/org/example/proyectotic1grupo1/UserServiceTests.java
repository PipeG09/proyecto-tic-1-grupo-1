package org.example.proyectotic1grupo1;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.Role;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.repositories.RoleRepository;
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
    private RoleRepository roleRepository;

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
    void testIsAdmin() {
        User user = new User();
        Role adminRole = new Role();
        adminRole.setName("ADMIN");

        user.setRoles(Set.of(adminRole));

        boolean isAdmin = userService.isAdmin(user);

        assertTrue(isAdmin);
    }

    @Test
    void testSave() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setFullname("Test User");
        userDto.setPassword("password");
        userDto.setIsAdmin(false);

        Role userRole = new Role();
        userRole.setName("USER");

        // Mockear los métodos necesarios
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Mockear el objeto User que será guardado
        User savedUser = new User();
        savedUser.setUsername("testUser");
        savedUser.setFullname("Test User");
        savedUser.setPassword("encodedPassword");
        savedUser.setRoles(Set.of(userRole));

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.save(userDto);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("Test User", result.getFullname());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void testUpdateUserProfile() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        user.setFullname("Old Fullname");

        UserDto userDto = new UserDto();
        userDto.setFullname("New Fullname");

        when(userRepository.findByUsername(username)).thenReturn(user);

        userService.updateUserProfile(username, userDto);

        assertEquals("New Fullname", user.getFullname());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUserById() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).delete(user);
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

    @Test
    void testUpdateUserById() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setFullname("Updated Fullname");

        User user = new User();
        user.setId(userId);
        user.setFullname("Old Fullname");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.updateUserById(userId, userDto);

        assertEquals("Updated Fullname", user.getFullname());
        verify(userRepository, times(1)).save(user);
    }
}

