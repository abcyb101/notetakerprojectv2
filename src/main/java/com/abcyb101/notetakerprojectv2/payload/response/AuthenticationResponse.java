package com.abcyb101.notetakerprojectv2.payload.response;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable { //not used
	//Serializable is a marker interface
	    private final String jwt;

	    public AuthenticationResponse(String jwt) {
	        this.jwt = jwt;
	    }

	    public String getJwt() {
	        return jwt;
	    }
	}

