package com.weble.linkedhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class LinkedHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkedHouseApplication.class, args);
	}

}
