package com.AuthGuard.AuthGuard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.AuthGuard.AuthGuard")
public class AuthGuardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthGuardApplication.class, args);
	}

}
