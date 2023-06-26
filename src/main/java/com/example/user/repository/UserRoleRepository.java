package com.example.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.models.Role;
import com.example.security.models.RoleName;

public interface UserRoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName name);

	boolean existsByName(RoleName roleUser);

}
