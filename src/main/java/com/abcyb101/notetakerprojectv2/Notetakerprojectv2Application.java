package com.abcyb101.notetakerprojectv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })//CHECK FOR PROD!
public class Notetakerprojectv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Notetakerprojectv2Application.class, args);
	}

}
