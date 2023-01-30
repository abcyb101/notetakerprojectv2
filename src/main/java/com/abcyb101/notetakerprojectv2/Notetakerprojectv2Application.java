package com.abcyb101.notetakerprojectv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
public class Notetakerprojectv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Notetakerprojectv2Application.class, args);
	}

}
