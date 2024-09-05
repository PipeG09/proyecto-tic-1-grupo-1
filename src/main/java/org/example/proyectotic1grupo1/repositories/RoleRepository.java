package org.example.proyectotic1grupo1.repositories;

import org.example.proyectotic1grupo1.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
