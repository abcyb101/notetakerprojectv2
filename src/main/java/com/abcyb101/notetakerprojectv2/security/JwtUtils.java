package com.abcyb101.notetakerprojectv2.security;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	@Value("${app.notetaker.secret}")//application properties
	private String secret;
	//private static final String SECRET; //cannot use static final, because injections are on-the-spot initializers. Workarounds possible, but discouraged

	/*
	public String generateJwtToken(Authentication authentication) {

	    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

	    return Jwts.builder()
	        .setSubject((userPrincipal.getUsername()))
	        .setIssuedAt(new Date())
	        .setExpiration(Date.from(
                    Instant.now().plus(15, ChronoUnit.DAYS))
            )
	        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
	        .compact();
	  }
	  */
	
	public String generateJwtToken(Authentication authentication) {

	    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

	    return Jwts.builder()
	        .setSubject((userPrincipal.getUsername()))
	        .setIssuedAt(new Date())
	        .setExpiration(Date.from(
                    Instant.now().plus(15, ChronoUnit.DAYS))
            )
	        .signWith(SignatureAlgorithm.HS512, secret)
	        .compact();
	  }
	
	private Key getSignInKey() { 
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
	
	//deprecated!
	/*
	public String getUserNameFromToken(String token) {
		return Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token).getBody().getSubject();
	}
	*/
	
	public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
	
	private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	
	public boolean validateJwtToken(String authToken) {
	      Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
	      return true;
	}
	
	public boolean isTokenValid(String token, String usernameCompare) {//UserDetails userDetails) {
		//deprecated! Also requires try/catch
		//return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        final String username = extractUsername(token);
        return (username.equals(usernameCompare)) && !isTokenExpired(token);
    }
	
	private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
