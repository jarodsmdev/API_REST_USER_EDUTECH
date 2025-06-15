package com.briones.users.management;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

	@PostConstruct
	public void printVars() {
		System.out.println(">>DB_ENDPOINT: " + System.getenv("DB_ENDPOINT"));
	}
}