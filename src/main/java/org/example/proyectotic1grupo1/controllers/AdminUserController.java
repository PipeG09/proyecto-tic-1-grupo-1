package org.example.proyectotic1grupo1.controllers;

import org.example.proyectotic1grupo1.dto.UserDto;
import org.example.proyectotic1grupo1.models.Role;
import org.example.proyectotic1grupo1.models.User;
import org.example.proyectotic1grupo1.repositories.RoleRepository;
import org.example.proyectotic1grupo1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user_list";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) throws Exception {
        User user = userService.findById(id);
        if (user == null) {
            throw new Exception("Usuario no encontrado");
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFullname(user.getFullname());
        // Convertir roles a una lista de IDs
        List<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        userDto.setRoleIds(roleIds);

        List<Role> allRoles = roleRepository.findAll();

        model.addAttribute("userDto", userDto);
        model.addAttribute("allRoles", allRoles);

        return "user_edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("userDto") UserDto userDto) throws Exception {
        userService.updateUserById(id, userDto);
        return "redirect:/admin/users?success";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUserById(id);
        return "redirect:/admin/users?deleted";
    }
}
