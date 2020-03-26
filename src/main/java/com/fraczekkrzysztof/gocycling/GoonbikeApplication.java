package com.fraczekkrzysztof.gocycling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GoonbikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoonbikeApplication.class, args);
	}

}
