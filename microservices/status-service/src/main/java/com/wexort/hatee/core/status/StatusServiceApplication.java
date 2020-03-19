package com.wexort.hatee.core.status;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.wexort.hatee")

public class StatusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatusServiceApplication.class, args);
	}

}
