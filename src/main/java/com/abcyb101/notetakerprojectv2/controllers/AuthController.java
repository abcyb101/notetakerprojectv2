package com.abcyb101.notetakerprojectv2.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abcyb101.notetakerprojectv2.models.accountModels.ERole;
import com.abcyb101.notetakerprojectv2.models.accountModels.Role;
import com.abcyb101.notetakerprojectv2.models.accountModels.User;
import com.abcyb101.notetakerprojectv2.payload.request.LoginRequest;
import com.abcyb101.notetakerprojectv2.payload.request.RegisterRequest;
import com.abcyb101.notetakerprojectv2.payload.response.JwtResponse;
import com.abcyb101.notetakerprojectv2.payload.response.MessageResponse;
import com.abcyb101.notetakerprojectv2.repositories.accountRepositories.RoleRepository;
import com.abcyb101.notetakerprojectv2.repositories.accountRepositories.UserRepository;
import com.abcyb101.notetakerprojectv2.security.JwtUtils;
import com.abcyb101.notetakerprojectv2.security.UserDetailsImpl;

@CrossOrigin(origins = "*")//cors
@RestController
@RequestMapping("/api/auth")
public class AuthController { //https://spring.io/guides/topicals/spring-security-architecture
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
				
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
		}
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email is already taken!"));
		}
		
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			roles.add(roleRepository.findByName(ERole.ROLE_USER).orElse(null));//orElseThrow as a way to give an Exception
		}	
		
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}	
}