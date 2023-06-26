package com.example.user.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.exceptions.RestException;
import com.example.security.models.Address;
import com.example.security.models.Role;
import com.example.security.models.RoleName;
import com.example.security.models.User;
import com.example.security.models.UserDetailsImpl;
import com.example.user.dto.UserRequestDto;
import com.example.user.dto.UserResponseDto;
import com.example.user.dto.UserUpdateRequestDto;
import com.example.user.repository.UserRepository;
import com.example.user.repository.UserRoleRepository;
import com.example.utility.UserRolesUtility;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User convert_userRequestDtoToUser(UserRequestDto udto) {
		User user = new User();
		user.setFirstName(udto.getFirstName());
		user.setLastName(udto.getLastName());
		user.setUsername(udto.getUsername());
		user.setPassword(udto.getPassword());
		user.setEmail(udto.getEmail());
		user.setPhone(udto.getPhone());
		
		Address address = new Address();
		address.setStreet(udto.getStreet());
		address.setCity(udto.getCity());
		address.setSuite(udto.getState());
		address.setZipcode(udto.getZipcode());
		user.setAddress(address);
		return user;
	}
	
	public User convert_userUpdateRequestDtoToUser(UserUpdateRequestDto udto) {
		User user = new User();
		user.setFirstName(udto.getFirstName());
		user.setLastName(udto.getLastName());
		user.setPhone(udto.getPhone());
		
		Address address = new Address();
		address.setStreet(udto.getStreet());
		address.setCity(udto.getCity());
		address.setSuite(udto.getState());
		address.setZipcode(udto.getZipcode());
		user.setAddress(address);
		return user;
	}
	
	public UserResponseDto convert_userToUserResponseDto(User u) {
		UserResponseDto user = new UserResponseDto();
		user.setId(u.getId());
		user.setFirstName(u.getFirstName());
		user.setLastName(u.getLastName());
		user.setUsername(u.getUsername());
		user.setEmail(u.getEmail());
		return user;
		
	}

	public UserResponseDto getCurrentUser(UserDetailsImpl currentUser) {
		return new UserResponseDto(currentUser.getId(), currentUser.getUsername(), currentUser.getFirstName(),
				currentUser.getLastName(), currentUser.getEmail(), UserRolesUtility.getRolesAsString(currentUser.getAuthorities()));
	}

	public Boolean checkUsernameAvailability(String username) {
		return !userRepository.existsByUsername(username);
	}

	public Boolean checkEmailAvailability(String email) {
		return !userRepository.existsByEmail(email);
	}

	public User addUser(@Valid UserRequestDto userRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(userRequest.getUsername()))) {
			throw new RestException(HttpStatus.BAD_REQUEST, "Username already exist");
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(userRequest.getEmail()))) {
			throw new RestException(HttpStatus.BAD_REQUEST, "Email already exist");
		}
		
		User user = this.convert_userRequestDtoToUser(userRequest);
		user.setActive(true);
		user.setAccountVerified(false);
		List<Role> roles = new ArrayList<>();
		roles.add(userRoleRepository.findByName(RoleName.ROLE_USER).orElseThrow(()->new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Default user role not exist")));
		user.setRoles(roles);
		user.setCreatedAt(Instant.now());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User updateUser(@Valid UserUpdateRequestDto userRequest, String username, UserDetailsImpl currentUser) {
		User oldUser = userRepository.getUserByName(username);
		if (oldUser.getId().equals(currentUser.getId())
				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			User user = convert_userUpdateRequestDtoToUser(userRequest);
			oldUser.setFirstName(user.getFirstName());
			oldUser.setLastName(user.getLastName());
			oldUser.setAddress(user.getAddress());
			oldUser.setPhone(user.getPhone());
			oldUser.setUpdatedAt(Instant.now());
			return userRepository.save(oldUser);
		}
		//ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
			//	"You don't have permission to update profile of: " + username);
		throw new RestException(HttpStatus.UNAUTHORIZED, "Permissions required to update user");
	}

	public String deleteUser(String username, UserDetailsImpl currentUser) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User not found"));
		if (!user.getId().equals(currentUser.getId())
				|| !currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			throw new RestException(HttpStatus.UNAUTHORIZED, "Permissions required to delete user");
		}

		userRepository.deleteById(user.getId());

		return "User deleted successfully";
	}

	public User changeRole(String username, String role) {
		User user = userRepository.getUserByName(username);
		List<Role> roles = new ArrayList<>();
		roles.add(userRoleRepository.findByName(RoleName.valueOf(role))
				.orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Role not found")));
		user.setRoles(roles);
		return userRepository.save(user);
	}

	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public long userCount() {
		return userRepository.count();
	}

}
