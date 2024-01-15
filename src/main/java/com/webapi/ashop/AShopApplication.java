package com.webapi.ashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AShopApplication.class, args);
	}

}
