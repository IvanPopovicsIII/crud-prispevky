package com.example.crudPrispevky;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CrudPrispevkyApplication {
	
	private static final Logger log = LoggerFactory.getLogger(CrudPrispevkyApplication.class); 

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(CrudPrispevkyApplication.class, args);

	}

}
