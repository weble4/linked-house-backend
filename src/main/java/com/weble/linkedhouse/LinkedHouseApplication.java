package com.weble.linkedhouse;

import jakarta.persistence.EntityListeners;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LinkedHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkedHouseApplication.class, args);
	}

}
