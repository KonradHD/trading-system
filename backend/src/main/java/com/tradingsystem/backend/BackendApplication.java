package com.tradingsystem.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		System.out.println("TEST backend");
		SpringApplication.run(BackendApplication.class, args);
	}

}
