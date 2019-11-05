package io.rainrobot.wake.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Service {
	public static void main(String[] args) {
		SpringApplication.run(Service.class, args);
	}
}
