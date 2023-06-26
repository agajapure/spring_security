package com.example.user.repository;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.security.models.User;
import com.example.security.models.UserDetailsImpl;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsername(@NotBlank String username);

	Optional<User> findByEmail(@NotBlank String email);

	Boolean existsByUsername(@NotBlank String username);

	Boolean existsByEmail(@NotBlank String email);

	Optional<User> findByAcccountVerificationCode(@NotBlank String acccountVerificationCode);

	Optional<User> findByUsernameOrEmail(String username, String email);

	default User getUser(UserDetailsImpl currentUser) {
		return getUserByName(currentUser.getUsername());
	}

	default User getUserByName(String username) {
		return findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));
	}

}
