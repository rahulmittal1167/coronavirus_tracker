package com.rahul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronavirusTrackerApplication {

	public static void main(String[] args) {
		System.out.println("Hey Welcome to Spring Boot Coronavirus Tracker");
		SpringApplication.run(CoronavirusTrackerApplication.class, args);
	}

}
