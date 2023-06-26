package com.example.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.exceptions.RestException;
import com.example.security.models.Role;
import com.example.security.models.RoleName;
import com.example.user.repository.UserRoleRepository;

@Service
public class UserRoleService {
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	public Role findByName(RoleName roleName) {
		return userRoleRepository.findByName(roleName).orElseThrow(()-> new RestException(HttpStatus.NOT_FOUND, "Role not found"));
	}

}
