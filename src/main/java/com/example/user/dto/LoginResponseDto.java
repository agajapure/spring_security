package com.example.user.dto;

public class LoginResponseDto {
	
	String token;

	public LoginResponseDto(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
}
