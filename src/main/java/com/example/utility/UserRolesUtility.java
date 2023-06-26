package com.example.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.example.security.models.Role;

public class UserRolesUtility {

	private UserRolesUtility() {
	}

	public static String getRolesAsString(Collection<? extends GrantedAuthority> authorities) {
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : authorities) {
			roles.add(grantedAuthority.getAuthority());
		}
		return String.join(",", roles.toArray(new String[roles.size()]));
	}

	public static String getRolesAsString(List<Role> roles) {
		List<String> newRoles = new ArrayList<>();
		for (Role role : roles) {
			newRoles.add(role.getName().name());
		}
		return String.join(",", roles.toArray(new String[newRoles.size()]));
	}

}
