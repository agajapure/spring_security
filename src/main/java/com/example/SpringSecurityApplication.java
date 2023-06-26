package com.example;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.exceptions.RestException;
import com.example.security.models.Role;
import com.example.security.models.RoleName;
import com.example.security.models.User;
import com.example.user.repository.UserRepository;
import com.example.user.repository.UserRoleRepository;

@SpringBootApplication
public class SpringSecurityApplication {
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}
	
	@Bean
	InitializingBean initDatabase() {
		return () -> {
			
			if (userRoleRepository.count() == 0) {
				for (RoleName roleName : RoleName.values()) {
					Role role = new Role();
					role.setName(roleName);
					userRoleRepository.save(role);
				}
			}

			if (userRepository.count() == 0) {
				BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
				User user = new User("Super", "Admin", "admin", "mgg@gmail.com", bcpe.encode("admin"));
				List<Role> roles = new ArrayList<>();
				roles.add(userRoleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Role not exist")));
				user.setRoles(roles);
				user.setActive(true);
				user.setAccountVerified(true);
				user.setCreatedAt(Instant.now());
				user = userRepository.save(user);
			}

		};
	}

}
