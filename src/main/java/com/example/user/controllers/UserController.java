package com.example.user.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.RestException;
import com.example.security.annotations.CurrentUser;
import com.example.security.models.User;
import com.example.security.models.UserDetailsImpl;
import com.example.user.dto.UserRequestDto;
import com.example.user.dto.UserResponseDto;
import com.example.user.dto.UserUpdateRequestDto;
import com.example.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users API", description = "")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/me")
	// @PreAuthorize("hasRole('USER') or hasRole('VENDOR')")
	public ResponseEntity<UserResponseDto> getCurrentUser(@CurrentUser UserDetailsImpl currentUser) {
		UserResponseDto user = userService.getCurrentUser(currentUser);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}


	@PostMapping
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> addUser(@Valid @RequestBody UserRequestDto userRequest) {
		if (Boolean.TRUE.equals(userService.existsByUsername(userRequest.getUsername()))) {
			throw new RestException(HttpStatus.BAD_REQUEST, "Username already exist");
		}

		if (Boolean.TRUE.equals(userService.existsByEmail(userRequest.getEmail()))) {
			throw new RestException(HttpStatus.BAD_REQUEST, "Email already exist");
		}
		// user.setAcccountVerificationCode(userAccountVarificationService.getVerificationCode(user));
		User newUser = userService.addUser(userRequest);
		// userAccountVarificationService.sendAccountVerificationEmail(newUser);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	// @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('VENDOR')")
	public ResponseEntity<User> updateUser(@Valid @RequestBody UserUpdateRequestDto user,
			@PathVariable(value = "username") String username, @CurrentUser UserDetailsImpl currentUser) {
		User updatedUSer = userService.updateUser(user, username, currentUser);

		return new ResponseEntity<>(updatedUSer, HttpStatus.CREATED);
	}

	@DeleteMapping("/{username}")
	// @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('VENDOR')")
	public ResponseEntity<String> deleteUser(@PathVariable(value = "username") String username,
			@CurrentUser UserDetailsImpl currentUser) {
		String response = userService.deleteUser(username, currentUser);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{username}/{role}/changeRole")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> changeRole(@PathVariable(name = "username") String username,
			@PathVariable(name = "role") String role, @CurrentUser UserDetailsImpl currentUser) {
		Object apiResponse = userService.changeRole(username, role);

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/accVerify")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> verifyUserAccount(@RequestParam(name = "code") String code) {
		// userService.verifyUserByAcccountVerificationCode(code);
		return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
	}
}
