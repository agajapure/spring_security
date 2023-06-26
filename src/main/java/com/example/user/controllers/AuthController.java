package com.example.user.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.RestException;
import com.example.security.jwt.JWTTokenUtil;
import com.example.security.models.User;
import com.example.user.dto.LoginRequestDto;
import com.example.user.dto.LoginResponseDto;
import com.example.user.dto.UserRequestDto;
import com.example.user.dto.UserResponseDto;
import com.example.user.service.UserService;
import com.example.utility.UserRolesUtility;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication API", description = "")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JWTTokenUtil tokenUtil;

	@Autowired
	private UserService userService;


	@PostMapping("/signin")
	public ResponseEntity<LoginResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = tokenUtil.generateJwtToken(authentication);
		return new ResponseEntity<>(new LoginResponseDto(token), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto signUpRequest) {
		if (Boolean.TRUE.equals(userService.existsByUsername(signUpRequest.getUsername()))) {
			throw new RestException(HttpStatus.NOT_ACCEPTABLE, "Username already exist");
		}

		if (Boolean.TRUE.equals(userService.existsByEmail(signUpRequest.getEmail()))) {
			throw new RestException(HttpStatus.NOT_ACCEPTABLE, "Email already exist");
		}

		// user.setAcccountVerificationCode(userAccountVarificationService.getVerificationCode(user));

		User newUser = userService.addUser(signUpRequest);

		// userAccountVarificationService.sendAccountVerificationEmail(newUser);

		// URI location =
		// ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
		// .buildAndExpand(result.getId()).toUri();

		UserResponseDto userDto = new UserResponseDto(newUser.getId(), newUser.getUsername(), newUser.getFirstName(),
				newUser.getLastName(), newUser.getEmail(), UserRolesUtility.getRolesAsString(newUser.getRoles()));

		return new ResponseEntity<>(userDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/checkUsernameAvailability")
	public ResponseEntity<Boolean> checkUsernameAvailability(@RequestParam(value = "username") String username) {
		Boolean isUsernameFound = userService.checkUsernameAvailability(username);

		return new ResponseEntity<>(isUsernameFound, HttpStatus.OK);
	}

	@GetMapping("/checkEmailAvailability")
	public ResponseEntity<Boolean> checkEmailAvailability(@RequestParam(value = "email") String email) {
		Boolean isEmailFound = userService.checkEmailAvailability(email);

		return new ResponseEntity<>(isEmailFound, HttpStatus.OK);
	}

}
