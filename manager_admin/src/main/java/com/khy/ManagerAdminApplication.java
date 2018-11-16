package com.khy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ManagerAdminApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ManagerAdminApplication.class, args);
	}

}
