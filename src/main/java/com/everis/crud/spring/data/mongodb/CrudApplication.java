package com.everis.crud.spring.data.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SpringBootApplication
public class CrudApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

}
