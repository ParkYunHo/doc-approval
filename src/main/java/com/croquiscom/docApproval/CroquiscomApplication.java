package com.croquiscom.docApproval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CroquiscomApplication {

	public static void main(String[] args) {
		SpringApplication.run(CroquiscomApplication.class, args);
	}

}
