package com.krovvidi.api.eventapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventAPIApplication {
	private static final Logger logger = LoggerFactory.getLogger(EventAPIApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EventAPIApplication.class, args);
	}
}
