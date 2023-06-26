package com.example.security.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstName;

	private String lastName;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private Boolean accountVerified;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String firstName, String lastName, String username, String email, String password,
			Boolean accountVerified, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.accountVerified = accountVerified;

		if (authorities == null) {
			this.authorities = null;
		} else {
			this.authorities = new ArrayList<>(authorities);
		}
	}

	public static UserDetailsImpl buildUser(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		return new UserDetailsImpl(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getEmail(), user.getPassword(), user.getAccountVerified(), authorities);
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities == null ? null : new ArrayList<>(authorities);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAccountVerified() {
		return accountVerified;
	}

	public void setAccountVerified(Boolean accountVerified) {
		this.accountVerified = accountVerified;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return getAccountVerified() == true ? true : false;
	}

	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		UserDetailsImpl that = (UserDetailsImpl) object;
		return Objects.equals(id, that.id);
	}

	public int hashCode() {
		return Objects.hash(id);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
