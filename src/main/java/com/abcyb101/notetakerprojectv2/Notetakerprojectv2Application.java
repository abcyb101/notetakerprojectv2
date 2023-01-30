package com.abcyb101.notetakerprojectv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
public class Notetakerprojectv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Notetakerprojectv2Application.class, args);
	}

}
