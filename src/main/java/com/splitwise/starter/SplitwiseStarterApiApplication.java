package com.splitwise.starter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com")
@EnableJpaRepositories("com.splitwise.model")
@EntityScan("com.splitwise.pojo")   
public class SplitwiseStarterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseStarterApiApplication.class, args);
	}

}
