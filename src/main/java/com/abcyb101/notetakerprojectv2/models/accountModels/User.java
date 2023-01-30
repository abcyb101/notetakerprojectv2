package com.abcyb101.notetakerprojectv2.models.accountModels;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
	uniqueConstraints = {
			@UniqueConstraint(columnNames = "username"),//be aware of enumeration attack
			@UniqueConstraint(columnNames = "email")
	})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	public User() {
		super();
	}
	
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();//there must be a better way
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
