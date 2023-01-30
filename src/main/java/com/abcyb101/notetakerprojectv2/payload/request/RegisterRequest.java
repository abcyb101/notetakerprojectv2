package com.abcyb101.notetakerprojectv2.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {
	
	@NotBlank(message = "Username cannot be empty")
	@Size(max = 32 , message = "Maximal 32 signs")
	private String username;
	
	@NotBlank(message = "Email cannot be empty")
	@Size(max = 254 , message = "Maximal 254 signs") //https://www.ietf.org/rfc/rfc2821.txt -> however double check with PostgreSQL
	@Email(message = "Please enter a valid email address")
	private String email;
	
	@NotBlank(message = "Password cannot be empty")
	@Size(min = 7, max = 128 , message = "Should ahve more than 7 signs, should have less than 128 signs")
	private String password;
	
	private Set<String> role;
	
	//Getters, Setters

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

}
