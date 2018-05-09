package com.emag.config;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.emag")
public class EmagApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(EmagApplication.class, args);
	}

}
