package com.gcit.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.gcit.lms")
public class LsmBootLibrarianApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsmBootLibrarianApplication.class, args);
	}

}
